package dev.thatsnasu.openirc;

public abstract class Command {
	public final Identifier identifier;
	
	public Command(String numeric, String named) {
		this.identifier = new Identifier(numeric, named);
	}
	
	public abstract boolean handle(String parameters);
	
	@Override
	public String toString() {
		return this.identifier.named+" ("+this.identifier.numeric+")";
	}
}