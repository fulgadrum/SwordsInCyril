package fftadata;
import java.io.Serializable;

public enum FFTACommand implements Serializable
{
	 NONE				(" - ",				new FFTASkill[]{}),
	 ITEM				("Item",			new FFTASkill[]{FFTASkill.POTION,		FFTASkill.HI_POTION,	FFTASkill.X_POTION,		FFTASkill.ETHER,		FFTASkill.ELIXIR,		FFTASkill.PHOENIX_DOWN,	FFTASkill.ECHO_SCREEN,	FFTASkill.MAIDEN_KISS,	FFTASkill.SOFT,			FFTASkill.BANDAGE,		FFTASkill.HOLY_WATER,	FFTASkill.ANTIDOTE,		FFTASkill.CUREALL,	FFTASkill.EYE_DROPS,	FFTASkill.DRAW_WEAPON}),
	 BATTLE_TECH_B		("Battle Tech",		new FFTASkill[]{FFTASkill.FIRST_AID,	FFTASkill.POWERBREAK,	FFTASkill.MINDBREAK,	FFTASkill.MAGICBREAK,	FFTASkill.SPEEDBREAK,	FFTASkill.BODY_SLAM,	FFTASkill.GREASED_BOLT,	FFTASkill.DOWNSIZE}),
	 DRAGON_TECH		("Dragon Tech",		new FFTASkill[]{FFTASkill.JUMP,			FFTASkill.LANCET,		FFTASkill.WYRMTAMER,	FFTASkill.FIRE_BREATH,	FFTASkill.BOLT_BREATH,	FFTASkill.ICE_BREATH,	FFTASkill.WYRMKILLER,	FFTASkill.BANGAA_CRY}),
	 DEFEND				("Defend",			new FFTASkill[]{FFTASkill.TREMOR,		FFTASkill.MELTDOWN,		FFTASkill.DEFENSE,		FFTASkill.DROP_WEAPON,	FFTASkill.HIBERNATE,	FFTASkill.MOW_DOWN,		FFTASkill.AURA,			FFTASkill.EXPERT_GUARD}),
	 SPELLBLADE_TECH	("Spellblade Tech",	new FFTASkill[]{FFTASkill.RUSH,			FFTASkill.WILD_SWING,	FFTASkill.BEATDOWN,		FFTASkill.BLITZ,		FFTASkill.FIRE_SWORD,	FFTASkill.BOLT_SWORD,	FFTASkill.ICE_SWORD,	FFTASkill.ULTIMA_SWORD}),
	 MONK_TECH			("Monk Tech",		new FFTASkill[]{FFTASkill.WHIRLWIND,	FFTASkill.AIR_RENDER,	FFTASkill.EARTH_RENDER,	FFTASkill.FAR_FIST,		FFTASkill.CHAKRA,		FFTASkill.REVIVE,		FFTASkill.EXORCISE,		FFTASkill.HOLY_SIGN}),
	 PRAYER				("Prayer Tech",		new FFTASkill[]{FFTASkill.CURA,			FFTASkill.DISPEL,		FFTASkill.HOLY,			FFTASkill.BARRIER,		FFTASkill.JUDGE,		FFTASkill.WATER,		FFTASkill.AERO,			FFTASkill.BREAK}),
	 SACRED_TECH		("Sacred Tech",		new FFTASkill[]{FFTASkill.ASTRA,		FFTASkill.WARCRY,		FFTASkill.RASP,			FFTASkill.CHEER,		FFTASkill.SILENCE,		FFTASkill.SOUL_SPHERE,	FFTASkill.HASTE,		FFTASkill.LIFEBREAK}),
	 BATTLE_TECH_H		("Battle Tech",		new FFTASkill[]{FFTASkill.FIRST_AID,	FFTASkill.POWERBREAK,	FFTASkill.MINDBREAK,	FFTASkill.MAGICBREAK,	FFTASkill.SPEEDBREAK,	FFTASkill.MUG,			FFTASkill.PROVOKE,		FFTASkill.SENSOR}),
	 CHIVALRY			("Chivalry",		new FFTASkill[]{FFTASkill.NURSE,		FFTASkill.DEFENSE,		FFTASkill.COVER,		FFTASkill.SUBDUE,		FFTASkill.DROP_WEAPON,	FFTASkill.PARLEY,		FFTASkill.SAINT_CROSS,	FFTASkill.HOLY_BLADE}),
	 FIGHTER_TECH		("Fighter Tech",	new FFTASkill[]{FFTASkill.RUSH,			FFTASkill.WILD_SWING,	FFTASkill.BEATDOWN,		FFTASkill.BLITZ,		FFTASkill.AIR_RENDER,	FFTASkill.FAR_FIST,		FFTASkill.AIR_BLAST,	FFTASkill.BACKDRAFT}),
	 STEAL				("Steal",			new FFTASkill[]{FFTASkill.STEAL_ARMOR,	FFTASkill.STEAL_SHIELD,	FFTASkill.STEAL_ACCESS,	FFTASkill.STEAL_HELM,	FFTASkill.STEAL_WEAPON,	FFTASkill.STEAL_GIL,	FFTASkill.STEAL_EXP,	FFTASkill.STEAL_JP,		FFTASkill.STEAL_ABILITY}),
	 NINJA_SKILL		("Ninja Skill",		new FFTASkill[]{FFTASkill.THROW,		FFTASkill.WOOD_VEIL,	FFTASkill.FIRE_VEIL,	FFTASkill.EARTH_VEIL,	FFTASkill.METAL_VEIL,	FFTASkill.WATER_VEIL,	FFTASkill.UNSPELL,		FFTASkill.OBLIVION}),
	 WHITE_MAGIC		("White Magic",		new FFTASkill[]{FFTASkill.CURE,			FFTASkill.CURA,			FFTASkill.CURAGA,		FFTASkill.LIFE,			FFTASkill.FULL_LIFE,	FFTASkill.AUTO_LIFE,	FFTASkill.SHELL,		FFTASkill.PROTECT}),
	 BLACK_MAGIC		("Black Magic",		new FFTASkill[]{FFTASkill.FIRE,			FFTASkill.FIRA,			FFTASkill.FIRAGA,		FFTASkill.THUNDER,		FFTASkill.THUNDARA,		FFTASkill.THUNDAGA,		FFTASkill.BLIZZARD,		FFTASkill.BLIZZARA,		FFTASkill.BLIZZAGA}),
	 PHANTASM_SKILL		("Phantasm Skill",	new FFTASkill[]{FFTASkill.PROMINENCE,	FFTASkill.TEMPEST,		FFTASkill.FREEZEBLINK,	FFTASkill.STAR_CROSS,	FFTASkill.STARDUST,		FFTASkill.DELUGE,		FFTASkill.SOIL_EVIDENCE,FFTASkill.WILD_TORNADO}),
	 BLUE_MAGIC			("Blue Magic",		new FFTASkill[]{FFTASkill.GOBLIN_PUNCH,	FFTASkill.MAGIC_HAMMER,	FFTASkill.ACID,			FFTASkill.BLOWUP,		FFTASkill.MIGHTY_GUARD,	FFTASkill.GUARD_OFF,	FFTASkill.DRAGON_FORCE,	FFTASkill.NIGHT,		FFTASkill.TWISTER,		FFTASkill.LV3_DEF_LESS,	FFTASkill.MATRA_MAGIC,	FFTASkill.POISON_CLAW,	FFTASkill.HASTEBREAK,	FFTASkill.BAD_BREATH,	FFTASkill.STARE,	FFTASkill.ROULETTE,	FFTASkill.DRAIN_TOUCH,	FFTASkill.LVQ_S_FLARE,	FFTASkill.WHITE_WIND,	FFTASkill.ANGEL_WHISPER}),
	 AIM				("Aim",				new FFTASkill[]{FFTASkill.BOOST,		FFTASkill.AIM_LEGS,		FFTASkill.AIM_ARM,		FFTASkill.CUPID,		FFTASkill.BURIAL,		FFTASkill.TAKE_AIM,		FFTASkill.FASTER,		FFTASkill.BLACKOUT}),
	 HUNT				("Hunt",			new FFTASkill[]{FFTASkill.SONIC_BOOM,	FFTASkill.OUST,			FFTASkill.ADVICE,		FFTASkill.AIM_VITALS,	FFTASkill.HUNTING,		FFTASkill.ADDLE,		FFTASkill.ULTIMA_SHOT,	FFTASkill.SIDEWINDER,	FFTASkill.CAPTURE}),
	 CALL				("Call",			new FFTASkill[]{FFTASkill.SHEEP_COUNT,	FFTASkill.WOOL,			FFTASkill.CUISINE,		FFTASkill.TAIL_WAG,		FFTASkill.CHOCOBO_RUSH,	FFTASkill.FROGSONG,		FFTASkill.FRIEND,		FFTASkill.CATNIP}),
	 CHARGE				("Charge",			new FFTASkill[]{FFTASkill.MOG_ATTACK,	FFTASkill.MOG_GUARD,	FFTASkill.MOG_LANCE,	FFTASkill.MOG_RUSH,		FFTASkill.MOG_SHIELD,	FFTASkill.MOG_PEEK,		FFTASkill.MOG_AID,		FFTASkill.ULTIMA_CHARGE}),
	 GUNMANSHIP			("Gunmanship",		new FFTASkill[]{FFTASkill.FIRESHOT,		FFTASkill.BOLTSHOT,		FFTASkill.ICESHOT,		FFTASkill.CONFUSHOT,	FFTASkill.CHARMSHOT,	FFTASkill.BLINDSHOT,	FFTASkill.SILENSHOT,	FFTASkill.STOPSHOT}),
	 STUNT				("Stunt",			new FFTASkill[]{FFTASkill.HURL,			FFTASkill.RING,			FFTASkill.FIREBOMB,		FFTASkill.BALL,			FFTASkill.DAGGER,		FFTASkill.SMILE,		FFTASkill.GIL_TOSS}),
	 PANDORA			("Pandora",			new FFTASkill[]{FFTASkill.RED_SPRING,	FFTASkill.BLUE_SCREW,	FFTASkill.GREEN_GEAR,	FFTASkill.SILVER_DISC,	FFTASkill.GOLD_BATTERY,	FFTASkill.BLACK_INGOT,	FFTASkill.CHROMA_GEM,	FFTASkill.YELLOW_SPRING}),
	 TIME_MAGIC			("Time Magic",		new FFTASkill[]{FFTASkill.HASTE,		FFTASkill.QUICKEN,		FFTASkill.SLOW,			FFTASkill.REFLECT,		FFTASkill.STOP,			FFTASkill.SILENCE,		FFTASkill.QUARTER,		FFTASkill.DEMI}),
	 ALCHEMY_SKILL		("Alchemy Skill",	new FFTASkill[]{FFTASkill.ASTRA,		FFTASkill.DEATH,		FFTASkill.METEOR,		FFTASkill.RASP,			FFTASkill.FLARE,		FFTASkill.POISON,		FFTASkill.TOAD}),	
	 CONTROL			("Control",			new FFTASkill[]{}),
	 MORPH				("Morph",			new FFTASkill[]{FFTASkill.GOBLIN,		FFTASkill.FLAN,			FFTASkill.BOMB,			FFTASkill.DRAGON,		FFTASkill.LAMIA,		FFTASkill.BUG,			FFTASkill.PANTHER,		FFTASkill.MALBORO,		FFTASkill.FLOATEYE}),
	 SAGACITY_SKILL		("Sagacity Skill",	new FFTASkill[]{FFTASkill.DRAIN,		FFTASkill.BLIND,		FFTASkill.WATER,		FFTASkill.AERO,			FFTASkill.RAISE,		FFTASkill.GIGA_FLARE,	FFTASkill.BIO,			FFTASkill.ULTIMA_BLOW}),	
	 LUNGE_TECH			("Lunge Tech",		new FFTASkill[]{FFTASkill.SWARMSTRIKE,	FFTASkill.SHADOWSTICK,	FFTASkill.CHECKMATE,	FFTASkill.FEATHERBLOW,	FFTASkill.SWALLOWTAIL,	FFTASkill.MANASTRIKE,	FFTASkill.PIERCETHROUGH,FFTASkill.NIGHTHAWK}),
	 SPIRIT_MAGIC		("Spirit Magic",	new FFTASkill[]{FFTASkill.FIRE_WHIP,	FFTASkill.EARTH_HEAL,	FFTASkill.WHITE_FLAME,	FFTASkill.SHINING_AIR,	FFTASkill.EVIL_GAZE,	FFTASkill.HEAVY_DUST,	FFTASkill.SLIPRAIN,		FFTASkill.ELEMENTALSHIFT}),
	 RED_MAGIC			("Red Magic",		new FFTASkill[]{FFTASkill.BARRIER,		FFTASkill.FIRE,			FFTASkill.THUNDER,		FFTASkill.BLIZZARD,		FFTASkill.CURE,			FFTASkill.SLEEP,		FFTASkill.POISON,		FFTASkill.DOUBLECAST}),
	 SUMMON_MAGIC		("Summon Magic",	new FFTASkill[]{FFTASkill.UNICORN,		FFTASkill.IFRIT,		FFTASkill.RAMUH,		FFTASkill.SHIVA,		FFTASkill.KIRIN,		FFTASkill.CARBUNCLE,	FFTASkill.PHOENIX,		FFTASkill.MADEEN}),
	 CORNER				("Corner",			new FFTASkill[]{FFTASkill.SHADOWBIND,	FFTASkill.LAST_BREATH,	FFTASkill.APHONIA,		FFTASkill.NIGHTMARE,	FFTASkill.AGUE,			FFTASkill.ROCKSEAL,		FFTASkill.OBLIVION,		FFTASkill.ULTIMA_MASHER}),
	 SHARPSHOOT			("Sharpshoot",		new FFTASkill[]{FFTASkill.DOUBLESHOT,	FFTASkill.BESO_TOXICO,	FFTASkill.DEATH_SICKLE,	FFTASkill.CONCEAL,		FFTASkill.DOOM_ARCHER,	FFTASkill.AIM_ARMOR,	FFTASkill.AIM_WEAPON,	FFTASkill.AIM_WALLET});
	
	public final String NAME;
	public final FFTASkill[] SKILLS;
	
	FFTACommand(String name, FFTASkill[] skills)
	{
		this.NAME = name;
		this.SKILLS = skills;
	}
	
	public String toString()
	{
		return NAME;
	}
}
