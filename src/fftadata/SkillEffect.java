package fftadata;

import java.io.Serializable;

public enum SkillEffect implements Serializable
{
	HEALING_1X																						(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, 1, preview, false, false, false, true, true);		}	public String applyEffect(SkillEffectResult result)
	{	return applyHealing(result);															}	}),
	
	FULLY_HEAL_HP																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	result.damage = (int) -state.units[result.target].unit.maxHP;
		return guaranteedSuccess(result);														}	public String applyEffect(SkillEffectResult result)
	{	return fullHealing(result);																}	}),
	
	REVIVE_HALF_HP																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	result.damage = (int) -(state.units[result.target].unit.maxHP / 2);
		return guaranteedSuccess(result);														}	public String applyEffect(SkillEffectResult result)
	{	return applyRevival(result, false);														}	}),
	
	REVIVE_FULL_HP																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	result.damage = (int) -state.units[result.target].unit.maxHP;
		return guaranteedSuccess(result);														}	public String applyEffect(SkillEffectResult result)
	{	return applyRevival(result, true);														}	}),
	
	FIGHT_DAMAGE																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, 1, preview, true, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	REGULAR_DAMAGE																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, 1, preview, false, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),

	REGULAR_DAMAGE_CAPPED																			(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, 1, preview, false, true, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	DOUBLE_DAMAGE																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, 2, preview, false, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	TRIPLE_DAMAGE																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, 3, preview, false, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	HALF_FIGHT_DAMAGE																			(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, .5, preview, true, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	HALF_DAMAGE																						(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, .5, preview, false, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	HALF_DAMAGE_NEVER_MISS																			(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, .5, preview, false, false, false, false, true);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	DOUBLE_DAMAGE_HALF_HIT																			(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, .5, 2, preview, false, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),
	
	HALF_DAMAGE_DOUBLE_HIT																			(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 2, .5, preview, false, false, false, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyDamage(result);																}	}),

	MP_DAMAGE																						(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return genericDamageEffect(result, 1, 1, preview, false, false, true, false, false);	}	public String applyEffect(SkillEffectResult result)
	{	return applyMPDamage(result);															}	}),
	
	EFF1DEP_DRAIN																					(new SkillEffectHandler() { public SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult  prev, boolean preview)
	{ 	return eff1DepDrain(result, prev);														}	public String applyEffect(SkillEffectResult result)
	{	return applyHealing(result);															}	});
	
	static GameState state;
	public final SkillEffectHandler handler;
	
	SkillEffect(SkillEffectHandler handler)
	{
		this.handler = handler;
	}
	
	public interface SkillEffectHandler
	{
		SkillEffectResult resolveEffect(SkillEffectResult result, SkillEffectResult prev, boolean preview); // determine effect
		String applyEffect(SkillEffectResult result); // apply effect
		// int[] predictEffect(SkillEffectResult result); // predict effect
	}
	
	public static void setGameState(GameState gameState)
	{
		state = gameState;
	}
		
	// Server-side effect handlers
	public static SkillEffectResult genericDamageEffect(SkillEffectResult result,
			double hitFactor, double damageFactor, boolean preview,
			boolean canCrit, boolean capToHP, boolean capToMP, boolean healing, boolean neverMiss)
	{
		ActiveUnit user = state.units[result.user];
		ActiveUnit target = state.units[result.target];
		FFTASkill skill = result.skill;
		
		int hitRate;
		if (neverMiss)
			hitRate = 100;
		else
			hitRate = FFTACalc.getATypeHitRate(user, target, skill, hitFactor);
		result.hitChance = hitRate;
		
		int rand = (int) (100 * Math.random());
		if (rand < hitRate || preview)
		{
			// Calc damage
			int dmg = FFTACalc.getDamage(user, target, skill, damageFactor, healing, canCrit, capToHP, capToMP, preview);
			result.critical = FFTACalc.wasCritical;
			
			// Save results
			result.damage = dmg;
			result.success = true;
		}
		else
			result.success = false;
		
		return result;
	}
	
	public static SkillEffectResult guaranteedSuccess(SkillEffectResult result)
	{
		result.hitChance = 100;
		result.success = true;
		return result;
	}
	
	public static SkillEffectResult eff1DepDrain(SkillEffectResult result, SkillEffectResult prev)
	{
		result.target = result.user;
		if (prev.success)
		{
			result.damage = prev.damage;
			result.success = true;
		}
		return result;
	}
	
	// Client-side effect handlers
	public static String applyDamage(SkillEffectResult result)
	{
		String report = "";
		
		ActiveUnit target = state.units[result.target];
		if (result.success)
		{
			// Treat absorbed damage as healing instead
			if (result.damage < 0)
				return applyHealing(result);
			
			state.applyDamage(result.target, result.damage);
			report = "<em><span style=\"color:gray\">......<strong>";
			if (result.critical)
				report += "</em><span style=\"color:red\">CRITICAL HIT!</span><em> ";  
			
			report += target.unit.name + "</strong> takes <strong><span style=\"color:red\">" + 
					result.damage + "</strong> damage! (" + result.hitChance + "%)";
		}
		else
			report = "<em><span style=\"color:gray\">......The attack misses <strong>" +
					target.unit.name + "</strong>! (" + result.hitChance + "%)";
				
		return report;
	}
	
	private static String applyMPDamage(SkillEffectResult result)
	{
		String report = "";
		
		ActiveUnit target = state.units[result.target];
		if (result.success)
		{
			// Treat absorbed damage as healing instead
			if (result.damage < 0)
				return applyMPHealing(result);
			
			state.applyMPDamage(result.target, result.damage);
			report = "<em><span style=\"color:gray\">......<strong>";
			if (result.critical)
				report += "</em><span style=\"color:red\">CRITICAL HIT!</span><em> ";  
			
			report += target.unit.name + "</strong> takes <strong><span style=\"color:fuchsia\">" + 
					result.damage + "</strong> MP damage! (" + result.hitChance + "%)";
		}
		else
			report = "<em><span style=\"color:gray\">......The attack misses <strong>" +
					target.unit.name + "</strong>! (" + result.hitChance + "%)";
				
		return report;
	}
	
	public static String applyHealing(SkillEffectResult result)
	{
		String report = "";
		ActiveUnit target = state.units[result.target];
		
		if (result.success && target.currHP > 0)
		{
			int healing = Math.abs(result.damage);
			state.applyHealing(result.target, healing);
			report = "<em><span style=\"color:gray\">......<strong>" + target.unit.name +
					"</strong> recovers <strong><span style=\"color:lime\">" + healing + "</strong> HP!";
		}
		return report;
	}
	
	public static String applyMPHealing(SkillEffectResult result)
	{
		String report = "";
		ActiveUnit target = state.units[result.target];
		
		if (result.success)
		{
			int healing = Math.abs(result.damage);
			state.applyMPHealing(result.target, healing);
			report = "<em><span style=\"color:gray\">......<strong>" + target.unit.name +
					"</strong> recovers <strong><span style=\"color:cyan\">" + healing + "</strong> MP!";
		}
		return report;
	}
	
	public static String applyRevival(SkillEffectResult result, boolean fullLife)
	{
		ActiveUnit target = state.units[result.target];
		String report = "";
		
		if (result.success && target.currHP == 0)
		{
			int healing = (int) target.unit.maxHP;
			if (fullLife)
				report += "<em><span style=\"color:gray\">......<strong>" + target.unit.name + "</strong> rises with full health!";
			else
			{
				healing /= 2;
				report += "<em><span style=\"color:gray\">......<strong>" + target.unit.name + "</strong> rises!";
			}
			state.applyHealing(result.target, healing);
		}
		return report;
	}
	
	public static String fullHealing(SkillEffectResult result)
	{
		String report = "";
		ActiveUnit target = state.units[result.target];
		
		if (result.success)
		{
			state.applyHealing(result.target, (int) target.unit.maxHP);
			report = "<em><span style=\"color:gray\">......<strong>" + target.unit.name +
					"</strong>'s HP is fully restored!";
		}
		return report;
	}

}
