package ua.nure.norkin.SummaryTask4.entity;

/**
 * Mark entity. Every instance is characterized by foreign keys from subject and
 * entrant, mark value and exam type (preliminary or diploma). So every mark
 * references to specific subject and entrant that get it on some exam.
 *
 * @author Mark Norkin
 *
 */
public class Mark extends Entity {

	private static final long serialVersionUID = -6225323023971292703L;
	private int subjectId;
	private int entrantId;
	private byte mark;
	private String examType;

	public Mark(int subjectId, int entrantId, byte mark, String examType) {
		super();
		this.subjectId = subjectId;
		this.entrantId = entrantId;
		this.mark = mark;
		this.examType = examType;
	}

	public Mark() {
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getEntrantId() {
		return entrantId;
	}

	public void setEntrantId(int entrantId) {
		this.entrantId = entrantId;
	}

	public byte getMark() {
		return mark;
	}

	public void setMark(byte mark) {
		this.mark = mark;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String markType) {
		this.examType = markType;
	}

	@Override
	public String toString() {
		return "Mark [subjectId=" + subjectId + ", entrantId=" + entrantId
				+ ", mark=" + mark + ", examType=" + examType + "]";
	}

}
