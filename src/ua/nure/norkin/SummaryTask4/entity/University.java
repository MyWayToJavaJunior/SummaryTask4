package ua.nure.norkin.SummaryTask4.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ua.nure.norkin.SummaryTask4.sorter.FacultySorter;

public class University extends Entity {

	private static final long serialVersionUID = 1133418774013168261L;
	private static final String name = "Kharkiv Politechtical Institute";
	private static final List<Faculty> faculties = new ArrayList<>();

	public static String getName() {
		return name;
	}

	public static List<Faculty> getFaculties() {
		return faculties;
	}

	public Collection<Faculty> sortFacultiesByName() {
		List<Faculty> result = new ArrayList<Faculty>(faculties);
		Collections.sort(result, FacultySorter.SORT_FACULTIES_BY_NAME);
		return result;
	}

	public Collection<Faculty> sortFacultiesByBudgetPlaces() {
		List<Faculty> result = new ArrayList<Faculty>(faculties);
		Collections.sort(result, FacultySorter.SORT_FACULTIES_BY_BUDGET_SEATS);
		return result;
	}

	public Collection<Faculty> sortFacultiesByTotalPlaces() {
		List<Faculty> result = new ArrayList<Faculty>(faculties);
		Collections.sort(result, FacultySorter.SORT_FACULTIES_BY_TOTAL_SEATS);
		return result;
	}

	public static boolean addAllFaculties(Collection<Faculty> faculties) {
		return faculties.addAll(faculties);
	}

	public boolean addFaculty(Faculty faculty) {
		return faculties.add(faculty);
	}

	public boolean removeFaculty(Faculty faculty) {
		return faculties.remove(faculty);
	}

}
