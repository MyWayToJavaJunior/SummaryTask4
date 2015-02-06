SELECT
        `facultyId`,
            university_admission.user.first_name,
            university_admission.user.last_name,
            university_admission.user.email,
            university_admission.entrant.isBlocked,
            `preliminary_sum`,
            `diploma_sum`,
            `preliminary_sum` + `diploma_sum` AS `total_sum`
    FROM
        (SELECT
        university_admission.faculty_entrants.Faculty_idFaculty AS `facultyId`,
            university_admission.mark.Entrant_idEntrant AS `entrantId`,
            SUM(CASE `exam_type`
                WHEN 'preliminary' THEN university_admission.mark.value
                ELSE 0
            END) AS `preliminary_sum`,
            SUM(CASE `exam_type`
                WHEN 'diploma' THEN university_admission.mark.value
                ELSE 0
            END) AS `diploma_sum`
    FROM
        university_admission.faculty_entrants
    INNER JOIN university_admission.mark ON university_admission.faculty_entrants.Entrant_idEntrant = university_admission.mark.Entrant_idEntrant
    GROUP BY entrantId) AS `entrant_marks_sum`
    INNER JOIN university_admission.faculty ON `entrant_marks_sum`.`entrantId` = university_admission.faculty.id
    INNER JOIN university_admission.entrant ON `entrantId` = university_admission.entrant.id
    INNER JOIN university_admission.user ON university_admission.entrant.User_idUser = university_admission.user.id
    WHERE
        facultyId = ?
    ORDER BY isBlocked ASC , `total_sum` DESC;