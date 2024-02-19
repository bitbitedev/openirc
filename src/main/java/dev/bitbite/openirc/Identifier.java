package dev.bitbite.openirc;

public enum Identifier {

	CAP(null),
	NICK(null),
	USER(null);

	private String numeric;
	
	Identifier(String numeric) {
		this.numeric = numeric;
	}

	public static Identifier of(String identifier) {
		if(valueOf(identifier) != null)
			return valueOf(identifier);

		for(Identifier i : Identifier.values())
			if(i.getNumeric() != null && i.getNumeric().equals(identifier))
				return i;

		return null;
	}

	public String getNumeric() {
		return this.numeric;
	}
}
