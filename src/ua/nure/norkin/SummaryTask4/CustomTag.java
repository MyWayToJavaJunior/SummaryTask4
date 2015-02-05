package ua.nure.norkin.SummaryTask4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Custom tag class which needed in apply for faculty user form. The main
 * purpose of this class is remove the need of validation in
 * <code>ApplyFacultyViewCommand</code>.
 *
 * For now the maximum mark in some subject is equal to 12, the lower one is
 * zero.
 *
 * @author Mark
 * @see ua.nure.norkin.SummaryTask4.command.faculty.ApplyFacultyViewCommand
 *
 */
public class CustomTag extends SimpleTagSupport {
	private static final List<Byte> marks = new ArrayList<Byte>();
	private int subjectId;
	private String examType;

	static {
		// populate marks
		for (byte i = 0; i <= 12; i++) {
			marks.add(i);
		}
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.println("<select name=\"" + subjectId + "_" + examType + "\">");
		for (Byte mark : marks) {
			out.println("<option>" + mark + "</option>");
		}
		out.println("</select>");
	}

}
