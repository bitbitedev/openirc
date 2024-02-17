package dev.bitbite.openirc;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import dev.bitbite.openirc.exceptions.DuplicateCommandException;
import dev.bitbite.openirc.exceptions.UnknownCommandException;
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
	
	public void process(Message message) throws UnknownCommandException {
		System.out.println(message.getMessageString());
		
		
		if(!this.numericIdentifiers.containsKey(message.command) && !this.namedIdentifiers.containsKey(message.command))
			throw new UnknownCommandException("Unknown command: \""+message.command+"\"");
		
		Identifier identifier = null;
		if(this.numericIdentifiers.containsKey(message.command)) identifier = this.numericIdentifiers.get(message.command);
		if(this.namedIdentifiers.containsKey(message.command)) identifier = this.namedIdentifiers.get(message.command);
		
		this.commands.get(identifier).handle(message);
	}
	
	public void loadCommands(IRCServer server) {
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
				this.registerCommand(commandClass.getDeclaredConstructor(IRCServer.class).newInstance(server));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (DuplicateCommandException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private void registerCommand(Command command) throws DuplicateCommandException {
		if(!command.identifier.numeric.equals("") && this.numericIdentifiers.containsKey(command.identifier.numeric)) throw new DuplicateCommandException("Command \""+command+"\" already registered");
		if(this.namedIdentifiers.containsKey(command.identifier.named)) throw new DuplicateCommandException("Command \""+command+"\" already registered");
		
		this.commands.put(command.identifier, command);
		this.namedIdentifiers.put(command.identifier.named, command.identifier);
		this.numericIdentifiers.put(command.identifier.numeric, command.identifier);
	}
	
	
}
