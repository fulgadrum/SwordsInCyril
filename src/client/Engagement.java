package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.HTMLDocument;

import fftadata.ActiveUnit;
import fftadata.EquipType;
import fftadata.FFTAEquip;
import fftadata.FFTAReaction;
import fftadata.FFTASkill;
import fftadata.GameState;
import fftadata.Morph;
import fftadata.SkillEffect;
import fftadata.SkillEffectResult;
import fftadata.StatusEffect;
import fftadata.Targeting;
import fftamap.*;
import zank.ZankGameAction;
import zank.ZankGameActionType;
import zank.ZankMessage;
import zank.ZankMessageType;
import zank.ZankUser;

public class Engagement
{
	Socket socket;
	ZankUser player, opponent;
	int playerNumber;
	String gameID;
	boolean gameOver;
	
	
	ObjectInputStream in;
	ObjectOutputStream out;
	FFTAMap map;
	ZankGameAction action;
	ZankMessage message;
	ZankClient client;
	
	ArrayList<ActiveUnit> p1Units, p2Units;
	
	EngagementWindow window;
	private GameState state;
	
	
	public Engagement(ZankUser player, int playerNumber, ZankUser opponent, String id, ZankClient client)
	{
		this.player = player;
		this.playerNumber = playerNumber;
		this.opponent = opponent;
		this.gameID = id;
		this.client = client;
		this.in = client.in;
		this.out = client.out;
		gameOver = false;
		
		map = MuscadetMapLoader.getMap(true);
		window = new EngagementWindow(this);
	}
	
	
	public void sendChat(String content) throws IOException
	{
		ZankGameAction action = new ZankGameAction(ZankGameActionType.CHAT, gameID, null, null, content);
		ZankMessage message = new ZankMessage(ZankMessageType.GAME, client.zu.name, action);
		client.sendZankMessage(message);
	}
	
	public void sendForfeit() throws IOException
	{
		ZankGameAction action = new ZankGameAction(ZankGameActionType.CHAT, gameID, null, null, null);
		ZankMessage message = new ZankMessage(ZankMessageType.LOGIN, player.name, action);
		client.sendZankMessage(message);
	}
	
	public void sendReady() throws IOException
	{
		ZankGameAction action = new ZankGameAction(ZankGameActionType.READY, gameID, null, null, window.getYourUnits());
		ZankMessage message = new ZankMessage(ZankMessageType.GAME, player.name, action);
		client.sendZankMessage(message);
	}
	
	public void sendMove() throws IOException
	{
		ActiveUnit au = currentUnit();
		int[] data = {au.id, au.x, au.y, au.z};
		ZankGameAction action = new ZankGameAction(ZankGameActionType.MOVE, gameID, null, null, data);
		ZankMessage message = new ZankMessage(ZankMessageType.GAME, player.name, action);
		client.sendZankMessage(message);
	}
	
	public void sendAction(int user, FFTASkill sk, int x, int y) throws IOException
	{
		int[] data = new int[5];
		data[0] = user;
		data[1] = sk.ordinal();
		data[2] = x;
		data[3] = y;
		data[4] = state.reacting ? 1 : 0;
		
		action = new ZankGameAction(ZankGameActionType.ACT, gameID, null, null, data);
		message = new ZankMessage(ZankMessageType.GAME, player.name, action);
		client.sendZankMessage(message);
	}
	
	public void sendDoublecast(int user1, FFTASkill sk1, int x1, int y1,
							   int user2, FFTASkill sk2, int x2, int y2) throws IOException
	{
		int[] data = new int[8];
		data[0] = user1;
		data[1] = sk1.ordinal();
		data[2] = x1;
		data[3] = y1; 
		data[4] = state.reacting ? 1 : 0;
		data[5] = sk2.ordinal();
		data[6] = x2;
		data[7] = y2;
		
		action = new ZankGameAction(ZankGameActionType.DOUBLECAST, gameID, null, null, data);
		message = new ZankMessage(ZankMessageType.GAME, player.name, action);
		client.sendZankMessage(message);
	}
	
	public void sendWait(int dir) throws IOException
	{
		int[] data = {state.currentUnit, dir};
		action = new ZankGameAction(ZankGameActionType.WAIT, gameID, null, null, data);
		message = new ZankMessage(ZankMessageType.GAME, player.name, action);
		
		client.sendZankMessage(message);
	}
	
	public void sendTurnTest(int ct) throws IOException
	{
		action = new ZankGameAction(ZankGameActionType.TURNTEST, gameID, null, null, ct);
		message = new ZankMessage(ZankMessageType.GAME, player.name, action);
		
		client.sendZankMessage(message);
	}
	
	public void sendExit() throws IOException
	{
		action = new ZankGameAction(ZankGameActionType.EXIT, gameID, null, null, null);
		message = new ZankMessage(ZankMessageType.GAME, client.zu.name, action);
		client.sendZankMessage(message);
	}
	
	public void sendUnmorph(int unit) throws IOException
	{
		action = new ZankGameAction(ZankGameActionType.UNMORPH, gameID, null, null, unit);
		message = new ZankMessage(ZankMessageType.GAME, client.zu.name, action);
		client.sendZankMessage(message);
	}
	
	// ZankMessage handlers
	// CHAT: append the message to the engagement chat and move the caret to the bottom
	public void receiveChat(String user, String msg)
	{
		window.appendToChat("<b>" + user + "</b>: " + msg);
	}
	
	// READY: register the received units to the rival team and create a list all the game's units in gamePanel
	// before telling gamePanel to start the game.
	public void receiveReady(ArrayList<ActiveUnit> units)
	{
		if (playerNumber == 1)
			p2Units = units;
		else if (playerNumber == 2)
			p1Units = units;
		
		ActiveUnit[] aus = new ActiveUnit[p1Units.size() + p2Units.size()];
		
		for (int i = 0; i < p1Units.size(); i++)
		{
			aus[i] = p1Units.get(i);
			aus[i].id = i;
		}
		
		for (int i = 0; i < p2Units.size(); i++)
		{
			aus[i + p1Units.size()] = p2Units.get(i);
			aus[i + p1Units.size()].id = i + p1Units.size();
		}
		
		state = new GameState(aus, map);
		beginGame();
		window.setupPreviews();
		System.out.println("Finished setting up previews, now beginning game");
		window.repaint();
	}
	
	// SPECREADY: create a list all the game's units in gamePanel
	// before telling gamePanel to start the game.
	public void receiveSpecReady(ArrayList<ActiveUnit>[] units)
	{
		p1Units = units[0];
		p2Units = units[1];
		
		ActiveUnit[] aus = new ActiveUnit[p1Units.size() + p2Units.size()];
		
		for (int i = 0; i < p1Units.size(); i++)
		{
			aus[i] = p1Units.get(i);
			aus[i].id = i;
		}
		
		for (int i = 0; i < p2Units.size(); i++)
		{
			aus[i + p1Units.size()] = p2Units.get(i);
			aus[i + p1Units.size()].id = i + p1Units.size();
		}
		
		state = new GameState(aus, map);
		beginGame();
		window.setupPreviews();
		System.out.println("Finished setting up previews, now beginning game");
		window.repaint();
	}
	
	public void receiveSpecJoin(GameState state)
	{
		p1Units = new ArrayList<ActiveUnit>();
		p2Units = new ArrayList<ActiveUnit>();
		
		for (ActiveUnit au : state.units)
			if (au.team == 1)
				p1Units.add(au);
			else if (au.team == 2)
				p2Units.add(au);
		
		this.state = state;
		
		state.map = MuscadetMapLoader.getMap(true);
		
		beginGame();
		window.setupPreviews();
		System.out.println("Finished setting up previews, now beginning game");
		window.repaint();
	}
	
	public void receiveSpecNotice(String username)
	{
		if (!username.equals(client.zu.name))
			window.appendToChat("<span color=\"silver\">(" + username + " is watching the engagement.)</span>");
	}
	
	// NEXT: initiate the given character's turn
	public void receiveNext(int[] data)
	{
		state.currentUnit = data[0];
		
		ActiveUnit au = currentUnit();
		System.out.println("--- " + au.unit.name + "'s turn");
		
		// Decrement this unit's Stop counter and check whether it has abated this turn
		boolean stopEnded = state.stopTick();
		
		if (au.status[StatusEffect.STOP.ordinal()] > 0 && !stopEnded)
			{}
		else
		{
			if (au.status[StatusEffect.SLEEP.ordinal()] > 0)
				window.appendToChat("<hr><em><span style=\"color:gray\"><strong>" + au.unit.name + "</strong> is asleep!");
			else if (stopEnded)
				window.appendToChat("<hr><em><span style=\"color:gray\"><strong>" + au.unit.name + "</strong> is back in time!");
			else if (au.status[StatusEffect.QUICK.ordinal()] > 0)
				window.appendToChat("<hr><em><span style=\"color:gray\"><strong>" + au.unit.name + "</strong> cuts in!");
			else if (au.status[StatusEffect.PETRIFY.ordinal()] == 0)
				window.appendToChat("<hr><em><span style=\"color:gray\"><strong>" + au.unit.name + "</strong> takes their turn!");
			
			if (au.status[StatusEffect.CHARM.ordinal()] > 0)
				window.appendToChat("<em><span style=\"color:gray\">...<strong>" + au.unit.name + "</strong> is charmed!");
			
			if (au.status[StatusEffect.CONFUSE.ordinal()] > 0)
				window.appendToChat("<em><span style=\"color:gray\">...<strong>" + au.unit.name + "</strong> is confused!");
			
			if (au.status[StatusEffect.BERSERK.ordinal()] > 0)
				window.appendToChat("<em><span style=\"color:gray\">...<strong>" + au.unit.name + "</strong> is berserk!");
			
			// Announce status effects that are abating this turn
			window.startOfTurnAnnouncements(au);
			
			// Apply poison damage and regen healing, if applicable
			int[] hpChanges = state.startOfTurnEffects(data[1], data[2]);		// data[1] is poisonVariance
			int poisonDamage = hpChanges[0];
			int regenHealing = hpChanges[1];
			
			if (regenHealing > 0)
			{
				window.appendToChat("<em><span style=\"color:gray\">...<strong>" + au.unit.name + 
						"</strong> regenerates <strong><span style=\"color: lime\">" + regenHealing + 
						"</strong></span> hit points!");
			}
			
			// Announce poison damage (if applicable) and update displays to reflect it
			if (poisonDamage > 0)
			{
				window.appendToChat("<em><span style=\"color:gray\">...<strong>" + au.unit.name + 
						"</strong> takes <strong><span style=\"color: red\">" + poisonDamage + 
						"</strong></span> damage from poison!");
				
				if (au.currHP == 0)
					window.appendToChat("<em><span style=\"color:gray\">......<strong>" + au.unit.name + " falls!");
			}
			
			window.updateSprite(getUnits()[au.id]);
			window.updateUnitPreview(au.id);
			
			// If the unit has died but has auto-life on, revive them
			boolean autoLifeRevived = state.checkAutoLife(au);
			if (autoLifeRevived)
			{
				window.appendToChat("<em><span style=\"color:gray\">.........<strong>" + au.unit.name +
						"</strong> is protected by <span style=\"color:aqua\"><strong>Auto-Life</strong></span>!");
				window.appendToChat("<em><span style=\"color:gray\">.........<strong>" + au.unit.name + "</strong> rises!");
				
				window.updateSprite(getUnits()[au.id]);
				window.updateUnitPreview(au.id);
			}
		}
		
		if (au.currHP > 0 && au.status[StatusEffect.PETRIFY.ordinal()] == 0 &&
							 au.status[StatusEffect.STOP.ordinal()]    == 0 && 
							 au.status[StatusEffect.SLEEP.ordinal()]   == 0 )
		{
			// Select current unit's tile
			window.selectTile(map.mapData[au.x][au.y]);
			
			// Decide which panel to show
			if (currentUnit().team == playerNumber)	// Show the action panel if it's your turn
				window.startPlayerTurn();
			else
				window.startRivalTurn();			// Show the blank panel if it's not
		}
		else
			window.startRivalTurn();
		
	}
	
	// MOVE: move the indicated unit using MapPanel's moveUnit method
	public void receiveMove(int[] data)
	{
		ActiveUnit au = state.units[data[0]];
		FFTAMapTile dest = map.mapData[data[1]][data[2]];
		window.moveUnit(au, dest);
	}
	
	// ACT: announce in chat that the active unit has taken the indicated action
	public void receiveAct(int[] data)
	{
		FFTASkill sk = FFTASkill.values[data[1]];
		int user = data[0], x = data[2], y = data[3];
		boolean restrictAoE = data[4] != 0 ? true : false;
			
		ArrayList<Integer> targets = state.getTargets(x, y, sk, state.units[user], restrictAoE);
		
		if (sk.NAME.equals("Fight"))
			window.appendToChat("<em><span style=\"color:gray\">...<strong>" + currentUnit().unit.name +
				"</strong> attacks <strong>" + state.units[targets.get(0)].unit.name + "</strong>!");
		else
		{
			String report = "<em><span style=\"color:gray\">...<strong>" + currentUnit().unit.name +
					"</strong> uses " + sk.NAME;
			if (targets.size() == 1)
				report += " on <strong>" + state.units[targets.get(0)].unit.name + "</strong>";
			window.appendToChat(report + "!");
		}
		
		state.expendMP(sk);
		window.updateUnitPreview(currentID());
	}
	
	public void receiveDoublecast(int[] data)
	{
		int user = data[0];
		window.appendToChat("<em><span style=\"color:gray\">...<strong>" + currentUnit().unit.name +
					"</strong> uses Doublecast!");
		FFTASkill sk1 = FFTASkill.values[data[2]], sk2 = FFTASkill.values[data[5]];
		state.expendMP(sk1);
		state.expendMP(sk2);
		window.updateUnitPreview(currentID());
	}
	
	// HIT: apply the effects of skills specified and announce the results in chat
	public void receiveHit(SkillEffectResult[] results)
	{
		boolean miss = true;
		ActiveUnit target = state.units[results[0].target];
		
		// Check cover
		if (results[0].cover != -1)
			window.appendToChat("<em><span style=\"color:gray\">......<strong>" + target.unit.name + 
								"</strong> <span style=\"color:blue\">swaps places with</span> <strong>" + 
								state.units[target.covering].unit.name + "</strong>!");
		
		// Check boost
		if (results[0].boost)
		{
			state.units[results[0].user].status[StatusEffect.BOOST.ordinal()] = 0;
			window.updateUnitPreview(results[0].user);
		}
		
		// Check reflect
		if (results[0].reflect)
			window.appendToChat("<em><span style=\"color:gray\">......<strong><span style=\"color:blue\">Reflected</span></strong>!");
			
		// determine whether the attack missed
		for (int i = 0; i < results.length; i++)
		{
			if (results[i] != null)
			{
				System.out.println(results[0].skill.NAME + ": Eff" + i + " success = " + results[i].success);
				if (results[i].success)
					miss = false;
			}
				
		}
		
		// Check multi-hit attack
		if (miss && results[0].skill != FFTASkill.DOUBLE_SWORD && results[0].skill != FFTASkill.DOUBLESHOT)
		{
			window.appendToChat("<em><span style=\"color:gray\">......The attack misses <strong>" +
					target.unit.name + "</strong>! (" + results[0].hitChance + "%)");
		}
		
		// apply each effect in sequence and append the report to chat
		for (int i = 0; i < results.length; i++)
		{	
			if (!results[i].dependent || results[0].success)
			{
				
				String report = state.applyEffect(results[i]);
				
				// Update target sprite to reflect any changes in HP
				window.updateSprite(getUnits()[results[i].target]);
				
				// Update preview panels to reflect any stat changes
				window.updateUnitPreview(results[i].target);
				
				// Append report to chat
				window.appendToChat(report);
			}
		}
		
		// Update user sprite (in case of recoil, death. etc...)
		ActiveUnit user = state.units[results[0].user];
		window.updateSprite(user);
		window.updateUnitPreview(user.id);

		// Check target death
		if (target.currHP == 0)
			window.appendToChat("<em><span style=\"color:gray\">.........<strong>" + target.unit.name + " falls!");
		
		
		// Check auto-life trigger
		if (results[results.length - 1].autoLife)
		{
			boolean autoLifeRevived = state.checkAutoLife(target);
			if (autoLifeRevived)
			{
				window.appendToChat("<em><span style=\"color:gray\">.........<strong>" + target.unit.name +
						"</strong> is protected by <span style=\"color:aqua\"><strong>Auto-Life</strong></span>!");
				window.appendToChat("<em><span style=\"color:gray\">.........<strong>" + target.unit.name + "</strong> rises!");
				
				// Update sprites and preview panels again
				window.updateSprite(getUnits()[target.id]);
				window.updateUnitPreview(target.id);
			}
		}
		
		// Check astra
		for (int j = 0; j < results.length; j++)
		{
			if (results[j].astra)
			{
				target.status[StatusEffect.ASTRA.ordinal()] = 0;
				j = results.length;
				window.appendToChat("<em><span style=\"color:gray\">.........<strong>" + target.unit.name +
						"</strong>'s <span style=\"color:blue\"><strong>Astra</strong></span> fades away...");
			}
		}
	}
	
	// DCHIT: display the individual skill name before applying the results
	public void receiveDCHit(SkillEffectResult[] results)
	{
		FFTASkill sk = results[0].skill;
		if (results[0].number == 0)
			window.appendToChat("<em><span style=\"color:gray\">...<strong>" + currentUnit().unit.name +
					"</strong> uses " + sk.NAME + "!");
		
		receiveHit(results);
	}
	
	// WAIT: change the indicated unit's facing in the MapPanel
	public void receiveWait(int[] data)
	{
		faceUnit(data[0], data[1]);
	}
	
	// LOSE: announce the winner's victory (or a draw, if both teams have 'lost') in chat.
	// If the current turn belongs to one of this unit's clients, and the game isn't over, bring up the facing panel.
	public void receiveGameOver(boolean[] data)
	{
		// Determine which player is p1 and which is p2
		String p1name, p2name;
		if (playerNumber == 1)
		{
			p1name = player.name;
			p2name = opponent.name;
		}
		else
		{
			p2name = player.name;
			p1name = opponent.name;
		}
		
		// If data[0] and data[1] are both true, the match has ended in a tie
		if (data[0] && data[1])
		{
			gameOver = true;
			window.setTitle(window.getTitle() + " - tie game!");
			window.appendToChat("<em><strong>The engagement has ended in a <span style=" +
					"\"text-decoration: underline; color:yellow\">tie</span></strong>");
		}
		
		// If data[0] is true, player *1* has LOST and player *2* has WON
		else if (data[0])
		{
			gameOver = true;
			window.setTitle(window.getTitle() + " - " + p2name + " wins!");
			window.appendToChat("<em><strong><span style=\"text-decoration: underline; color:blue\">" +
					p2name + "</span> has won the engagement!</strong>");
		}
		
		// If data[1] is true, player *2* has LOST and player *1* has WON
		else if (data[1])
		{
			gameOver = true;
			window.setTitle(window.getTitle() + " - " + p1name + " wins!"); 
			window.appendToChat("<em><strong><span style=\"text-decoration: underline; color:red\">" +
					p1name + "</span> has won the engagement!</strong>");
		}
		
		// If neither data[0] nor [1] is true, let the player finish their action and keep playing
		else
		{
			if (isYourTurn())
				window.finishAct();
		}
	}
	
	public void receiveExit(String username)
	{
		if (username.equals(player.name) || username.equals(opponent.name))
			window.appendToChat("<em><strong>" + username + " has left the room.</strong>");
		else
			window.appendToChat("<span color=\"silver\">(" + username + " has stopped watching.)</span>");

	}
	
	public void receiveReaction(int[] data)
	{
		FFTAReaction reaction = FFTAReaction.values()[data[1]];
		ActiveUnit reactor = getUnit(data[0]);
		int amt = data[2];
		
		window.appendToChat("<em><span style=\"color:gray\">...<strong>" + reactor.unit.name +
							"</strong>'s </em><span style=\"color:red\">" + reaction.toString() +
							"<span style=\"color:gray\"><em> activates!");
		
		switch (reaction)
		{
			case ABSORB_MP:
			{
				state.applyMPHealing(data[0], amt);
				String report = "<em><span style=\"color:gray\">......<strong>" + reactor.unit.name +
						"</strong> recovers <strong><span style=\"color:cyan\">" + amt + "</strong> MP!";
				window.appendToChat(report);
				break;
			}
			case AUTO_REGEN:
			{
				state.applyStatus(reactor, StatusEffect.REGEN);
				String report = "<em><span style=\"color:gray\">......<strong>" + reactor.unit.name +
						 "</strong>" + StatusEffect.REGEN.REPORT;
				window.appendToChat(report);
				break;
			}
			case DRAGONHEART: 
			{
				state.applyStatus(reactor, StatusEffect.AUTO_LIFE);
				String report = "<em><span style=\"color:gray\">......<strong>" + reactor.unit.name +
						 "</strong>" + StatusEffect.AUTO_LIFE.REPORT;
				window.appendToChat(report);
				break;
			}
			case LAST_HASTE:
			{
				state.applyStatus(reactor, StatusEffect.HASTE);
				String report = "<em><span style=\"color:gray\">......<strong>" + reactor.unit.name +
						"</strong>" + StatusEffect.HASTE.REPORT;
				window.appendToChat(report);
				break;
			}
			
			case LAST_QUICKEN:
			{
				state.applyStatus(reactor, StatusEffect.QUICK);
				String report = "<em><span style=\"color:gray\">......<strong>" + reactor.unit.name +
						 "</strong>" + StatusEffect.QUICK.REPORT;
				window.appendToChat(report);
				break;
			}
			
			case RETURN_FIRE:
			{
				String report = "<em><span style=\"color:gray\">......<strong>" + reactor.unit.name +
						 "</strong> throws the arrow back!";
				window.appendToChat(report);
				break;
			}
			
			default:
				break;
		}
	}
	
	public void receiveTurnOrder(int[] data)
	{
		window.updateTurnOrder(data);
	}
	
	public void receiveUnmorph(int unit)
	{
		ActiveUnit au = state.units[unit];
		String report = "<em><span style=\"color:gray\">...<strong>" + au.unit.name +
		 		"</strong> unmorphs!";
		window.appendToChat(report);
		
		if (au.team != playerNumber)
			au.morph = Morph.NONE;
	}
	
	public void beginGame()
	{
		ArrayList<ActiveUnit> otherTeam;
		if (playerNumber == 1)
			otherTeam = p2Units;
		else if (playerNumber == 2)
			otherTeam = p1Units;
		else
		{
			otherTeam = new ArrayList<ActiveUnit>();
			for (ActiveUnit au : p1Units) otherTeam.add(au);
			for (ActiveUnit au : p2Units) otherTeam.add(au);
		}
		
		window.beginGame(otherTeam);
	}
	
	public void faceTowardTile(ActiveUnit unit, FFTAMapTile tile)
	{
		int x1 = unit.x, y1 = unit.y;
		int x2 = tile.x, y2 = tile.y;
		
		int d_x = x2 - x1, d_y = y2 - y1; 
		
		if (d_x > d_y)
		{
			if (d_x > 0)
				unit.face("SW");
			else if (d_y < 0)
				unit.face("NE");
		}
		else if (d_y > d_x)
		{
			if (d_y > 0)
				unit.face("SE");
			else if (d_y < 0)
				unit.face("NW");
		}
	}
	
	public void faceUnit(int unit, int dir)
	{
		ActiveUnit au = getUnit(unit);
		au.face(dir);
		window.updateSprite(au);
	}
	
	// Returns true if the unit who is active belongs to this client's team
	public boolean isYourTurn()
	{
		return currentUnit().team == playerNumber;
	}
	
	public void setCurrentUnit(int currentUnit)
	{
		state.currentUnit = currentUnit;
	}
	
	public ActiveUnit currentUnit()
	{
		return state.units[state.currentUnit];
	}
	
	public ActiveUnit getUnit(int index)
	{
		return state.units[index];
	}
	
	public ActiveUnit[] getUnits()
	{
		return state.units;
	}
	
	public int currentID()
	{
		return state.currentUnit;
	}
	
	public ArrayList<Integer> getTargets(int x, int y, FFTASkill sk, ActiveUnit user)
	{
		return state.getTargets(x,  y, sk, user, false);
	}
	
	public GameState getState()
	{
		return state;
	}
}
