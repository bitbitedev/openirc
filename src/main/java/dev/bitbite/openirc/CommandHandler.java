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
	
	private IRCServer ircServer;
	private HashMap<Identifier, Command> commands;
	
	public CommandHandler(IRCServer ircServer, String packageName) {
		this.ircServer = ircServer;
		this.packageName = packageName;
		this.commands = new HashMap<Identifier, Command>();
	}
	
	public void process(Message message) throws UnknownCommandException {
		Identifier identifier = Identifier.of(message.command);
		if(identifier == null)
			throw new UnknownCommandException("Command \""+message.command+"\" not found");
		
		Command command = this.commands.get(identifier);
		if(command == null)
			throw new UnknownCommandException("Command \""+message.command+"\" not found");

		this.commands.get(identifier).handle(this.ircServer, message);
	}
	
	public void loadCommands() {
		try (ScanResult scanResult = new ClassGraph()
				// .verbose()
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
				this.registerCommand(
					Identifier.valueOf(commandClass.getSimpleName()), 
					commandClass.getDeclaredConstructor().newInstance());
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
	
	private void registerCommand(Identifier identifier, Command command) throws DuplicateCommandException {
		if(identifier == null)
			throw new IllegalArgumentException("Identifier cannot be null");

		if(this.commands.containsKey(identifier))
			throw new DuplicateCommandException("Command \""+identifier.name()+"\" already exists");
		
		System.out.println("Registering command "+identifier.name());
		this.commands.put(identifier, command);
	}
	
	
}
