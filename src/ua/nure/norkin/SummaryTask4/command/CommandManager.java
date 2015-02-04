package ua.nure.norkin.SummaryTask4.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.command.faculty.AddFacultyCommand;
import ua.nure.norkin.SummaryTask4.command.faculty.ApplyFacultyViewCommand;
import ua.nure.norkin.SummaryTask4.command.faculty.DeleteFacultyCommand;
import ua.nure.norkin.SummaryTask4.command.faculty.EditFacultyCommand;
import ua.nure.norkin.SummaryTask4.command.faculty.ViewAllFacultiesCommand;
import ua.nure.norkin.SummaryTask4.command.faculty.ViewEntrantCommand;
import ua.nure.norkin.SummaryTask4.command.faculty.ViewFacultyCommand;
import ua.nure.norkin.SummaryTask4.command.profile.EditProfileCommand;
import ua.nure.norkin.SummaryTask4.command.profile.LoginCommand;
import ua.nure.norkin.SummaryTask4.command.profile.LogoutCommand;
import ua.nure.norkin.SummaryTask4.command.profile.ViewProfileCommand;
import ua.nure.norkin.SummaryTask4.command.subject.AddSubjectCommand;
import ua.nure.norkin.SummaryTask4.command.subject.EditSubjectCommand;
import ua.nure.norkin.SummaryTask4.command.subject.ViewAllSubjectsCommand;
import ua.nure.norkin.SummaryTask4.command.subject.ViewSubjectCommand;

/**
 * Class that manages all commands.
 *
 * @author Mark Norkin
 *
 */
public class CommandManager {

	private static final Logger LOG = Logger.getLogger(CommandManager.class);

	private static Map<String, Command> commands = new HashMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("viewProfile", new ViewProfileCommand());
		commands.put("editProfile", new EditProfileCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("viewFaculty", new ViewFacultyCommand());
		commands.put("viewAllFaculties", new ViewAllFacultiesCommand());

		// client commands
		commands.put("client_registration", new ClientRegistrationCommand());
		commands.put("applyFaculty", new ApplyFacultyViewCommand());
		// admin commands
		commands.put("admin_registration", new AdminRegistrationCommand());
		commands.put("editFaculty", new EditFacultyCommand());
		commands.put("addFaculty", new AddFacultyCommand());
		commands.put("deleteFaculty", new DeleteFacultyCommand());
		commands.put("addSubject", new AddSubjectCommand());
		commands.put("editSubject", new EditSubjectCommand());
		commands.put("viewAllSubjects", new ViewAllSubjectsCommand());
		commands.put("viewSubject", new ViewSubjectCommand());
		commands.put("viewEntrant", new ViewEntrantCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Total number of commands equals to " + commands.size());
	}

	/**
	 * Returns command object which execution will give path to the resource.
	 *
	 * @param commandName
	 *            Name of the command.
	 * @return Command object if container contains such command, otherwise
	 *         specific <code>noCommand</code object will be returned.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found with name = " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}