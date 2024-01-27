package dev.thatsnasu.openirc;

import java.util.HashMap;

import dev.thatsnasu.openirc.exceptions.DuplicateCommandException;
import dev.thatsnasu.openirc.exceptions.UnknownCommandException;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class CommandHandler {
	private String packageName;
	
	private HashMap<Identifier, Command> commands;
	private HashMap<String, Identifier> numericIdentifiers;
	private HashMap<String, Identifier> namedIdentifiers;
	
	public CommandHandler(String packageName) {
		this.packageName = packageName;
		this.commands = new HashMap<Identifier, Command>();
		this.numericIdentifiers = new HashMap<String, Identifier>();
		this.namedIdentifiers = new HashMap<String, Identifier>();
	}
	
	@SuppressWarnings("deprecation")
	public void loadCommands() {
		try (ScanResult scanResult = new ClassGraph()
				.verbose()
				.enableAllInfo()
				.acceptPackages(this.packageName)
				.scan())
		{
			ClassInfoList routeClassInfoList = scanResult.getAllClasses();
			for(ClassInfo routeClassInfo : routeClassInfoList) {
				System.out.println(routeClassInfo.getName());
				
				Class<?> scannedClass = Class.forName(routeClassInfo.getName());
				if(!Command.class.isAssignableFrom(scannedClass)) continue;
				@SuppressWarnings("unchecked")
				Class<? extends Command> commandClass = (Class<? extends Command>) scannedClass;
				this.registerCommand(commandClass.newInstance());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (DuplicateCommandException e) {
			e.printStackTrace();
		}
	}
	
	private void registerCommand(Command command) throws DuplicateCommandException {
		if(this.numericIdentifiers.containsKey(command.identifier.numeric)) throw new DuplicateCommandException("Command \""+command+"\" already registered");
		if(this.namedIdentifiers.containsKey(command.identifier.named)) throw new DuplicateCommandException("Command \""+command+"\" already registered");
		
		this.commands.put(command.identifier, command);
		this.namedIdentifiers.put(command.identifier.named, command.identifier);
		this.numericIdentifiers.put(command.identifier.numeric, command.identifier);
	}
	
	public boolean processMessage(String message) throws UnknownCommandException {
		System.out.println(message);
		
		return false;
	}
}
