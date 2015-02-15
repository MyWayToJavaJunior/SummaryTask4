package ua.nure.norkin.SummaryTask4.repository.factory;

import ua.nure.norkin.SummaryTask4.repository.EntrantRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultyEntrantsRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultyRepository;
import ua.nure.norkin.SummaryTask4.repository.FacultySubjectsRepository;
import ua.nure.norkin.SummaryTask4.repository.ReportSheetRepository;
import ua.nure.norkin.SummaryTask4.repository.SubjectRepository;
import ua.nure.norkin.SummaryTask4.repository.UserRepository;

public abstract class RepositoryFactory {

	public static RepositoryFactory getFactoryByName(FactoryType factoryType) {

		switch (factoryType) {
		case MYSQL_REPOSITORY_FACTORY:
			return MySQLRepositoryFactory.getInstance();
		default:
			throw new UnsupportedOperationException("no such factory");
		}
	}

	public abstract UserRepository getUserRepository();

	public abstract EntrantRepository getEntrantRepository();

	public abstract FacultyRepository getFacultyRepository();

	public abstract SubjectRepository getSubjectRepository();

	public abstract FacultyEntrantsRepository getFacultyEntrantsRepository();

	public abstract FacultySubjectsRepository getFacultySubjectsRepository();

	public abstract ReportSheetRepository getReportSheetRepository();
}
