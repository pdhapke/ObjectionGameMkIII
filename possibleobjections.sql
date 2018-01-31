USE objection_database; 
DELETE FROM objection; 

ALTER TABLE objection
MODIFY objection_type VARCHAR(70);
ALTER TABLE objection
MODIFY explanation VARCHAR(2600);

-- INSERT INTO objections VALUES
-- (key, rule, type, explanation); 


INSERT INTO objection VALUES
(1, 103, "Argumentative", "MOCK TRIAL: Rule 103. Harassing not permitted
 Harassing a witness is not permitted on direct or cross-examination. 
MOCK TRIAL: Rule 1101. Argumentative/Badgering.
Any question that is essentially an argument to the trier of fact is improper. Badgering the witness
is not allowed. 
http://c.ymcdn.com/sites/www.iowabar.org/resource/resmgr/mock_tria_/mock_trial_rules_2011.pdf"); 

INSERT INTO objection VALUES
(2, 103, "Badgering", "MOCK TRIAL: Rule 103. Harassing not permitted
 Harassing a witness is not permitted on direct or cross-examination. 
MOCK TRIAL: Rule 1101. Argumentative/Badgering.
Any question that is essentially an argument to the trier of fact is improper. Badgering the witness
is not allowed. 
http://c.ymcdn.com/sites/www.iowabar.org/resource/resmgr/mock_tria_/mock_trial_rules_2011.pdf"); 

INSERT INTO objection VALUES
(3, 402, "Relevance", "Rule 402. General Admissibility of Relevant Evidence
Relevant evidence is admissible unless any of the following provides
otherwise:
• the United States Constitution;
• a federal statute;
• these rules; or
• other rules prescribed by the Supreme Court.
Irrelevant evidence is not admissible.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.)  
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence."); 

INSERT INTO objection VALUES
(4, 403, "Asked and Answered",  "
Rule 403. Excluding Relevant Evidence for Prejudice, Confusion,
Waste of Time, or Other Reasons
The court may exclude relevant evidence if its probative value
is substantially outweighed by a danger of one or more of the following:
unfair prejudice, confusing the issues, misleading the jury,
undue delay, **wasting time**, or needlessly presenting cumulative
evidence.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
Rule 1102. Asked and answered.
Questions and answers previously elicited and made by the same party should not be repeated
constantly. The cross-examiner may ask the same questions previously asked during the direct
examination. 
http://c.ymcdn.com/sites/www.iowabar.org/resource/resmgr/mock_tria_/mock_trial_rules_2011.pdf"); 


INSERT INTO objection VALUES
(5, 403, "Narrative", "
Rule 403. Excluding Relevant Evidence for Prejudice, Confusion,
Waste of Time, or Other Reasons
The court may exclude relevant evidence if its probative value
is substantially outweighed by a danger of one or more of the following:
unfair prejudice, confusing the issues, misleading the jury,
undue delay, **wasting time**, or **needlessly presenting cumulative
evidence**.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.

MOCK TRIAL - Rule 104. Narration
Witnesses shall not be permitted to give narrative testimony. Testimony of witnesses must
proceed by question and answer. 
http://c.ymcdn.com/sites/www.iowabar.org/resource/resmgr/mock_tria_/mock_trial_rules_2011.pdf
"); 

INSERT INTO objection VALUES 
(6, 403, "Compound", 
"Rule 403. Excluding Relevant Evidence for Prejudice, Confusion,
Waste of Time, or Other Reasons
The court may exclude relevant evidence if its probative value
is substantially outweighed by a danger of one or more of the following:
unfair prejudice, **confusing the issues**, misleading the jury,
undue delay, wasting time, or needlessly presenting cumulative
evidence.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(7, 403, "Confuses the Issues", 
"Rule 403. Excluding Relevant Evidence for Prejudice, Confusion,
Waste of Time, or Other Reasons
The court may exclude relevant evidence if its probative value
is substantially outweighed by a danger of one or more of the following:
unfair prejudice, **confusing the issues**, misleading the jury,
undue delay, wasting time, or needlessly presenting cumulative
evidence.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(8, 403, "More Prejudicial then Probative", 
"Rule 403. Excluding Relevant Evidence for Prejudice, Confusion,
Waste of Time, or Other Reasons
The court may exclude relevant evidence if its **probative value
is substantially outweighed by a danger of one or more of the following:
unfair prejudice**, confusing the issues, misleading the jury,
undue delay, wasting time, or needlessly presenting cumulative
evidence.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(9, 403, "Cumulative", 
"Rule 403. Excluding Relevant Evidence for Prejudice, Confusion,
Waste of Time, or Other Reasons
The court may exclude relevant evidence if its probative value
is substantially outweighed by a danger of one or more of the following:
unfair prejudice, confusing the issues, misleading the jury,
undue delay, wasting time, or **needlessly presenting cumulative
evidence**.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(10, 404, "404 - Improper Character Evidence", 
"Rule 404. Character Evidence; Crimes or Other Acts
(a) CHARACTER EVIDENCE.
(1) Prohibited Uses. Evidence of a person’s character or character
trait is not admissible to prove that on a particular occasion
the person acted in accordance with the character or
trait.
(2) Exceptions for a Defendant or Victim in a Criminal Case. The
following exceptions apply in a criminal case:
(A) a defendant may offer evidence of the defendant’s
pertinent trait, and if the evidence is admitted, the prosecutor
may offer evidence to rebut it;
(B) subject to the limitations in Rule 412, a defendant
may offer evidence of an alleged victim’s pertinent trait,
and if the evidence is admitted, the prosecutor may:
(i) offer evidence to rebut it; and
(ii) offer evidence of the defendant’s same trait; and
(C) in a homicide case, the prosecutor may offer evidence
of the alleged victim’s trait of peacefulness to rebut evidence
that the victim was the first aggressor.
(3) Exceptions for a Witness. Evidence of a witness’s character
may be admitted under Rules 607, 608, and 609.
(b) CRIMES, WRONGS, OR OTHER ACTS.
(1) Prohibited Uses. Evidence of a crime, wrong, or other act
is not admissible to prove a person’s character in order to
show that on a particular occasion the person acted in accordance
with the character.
(2) Permitted Uses; Notice in a Criminal Case. This evidence
may be admissible for another purpose, such as proving motive,
opportunity, intent, preparation, plan, knowledge, identity,
absence of mistake, or lack of accident. On request by a
defendant in a criminal case, the prosecutor must: 
(A) provide reasonable notice of the general nature of
any such evidence that the prosecutor intends to offer at
trial; and
(B) do so before trial—or during trial if the court, for
good cause, excuses lack of pretrial notice. 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(11, 608, "608 - Improper Character Evidence", 
"Rule 608. A Witness’s Character for Truthfulness or Untruthfulness
(a) REPUTATION OR OPINION EVIDENCE. A witness’s credibility
may be attacked or supported by testimony about the witness’s
reputation for having a character for truthfulness or untruthfulness,
or by testimony in the form of an opinion about that character.
But evidence of truthful character is admissible only after
the witness’s character for truthfulness has been attacked. 
(b) SPECIFIC INSTANCES OF CONDUCT. Except for a criminal conviction
under Rule 609, extrinsic evidence is not admissible to
prove specific instances of a witness’s conduct in order to attack
or support the witness’s character for truthfulness. But the court
may, on cross-examination, allow them to be inquired into if they
are probative of the character for truthfulness or untruthfulness
of:
(1) the witness; or
(2) another witness whose character the witness being crossexamined
has testified about.
By testifying on another matter, a witness does not waive any
privilege against self-incrimination for testimony that relates
only to the witness’s character for truthfulness.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 25, 1988, eff. Nov.
1, 1988; Mar. 27, 2003, eff. Dec. 1, 2003; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");


INSERT INTO objection VALUES 
(12, 407, "Subsequent Remedial Measures", 
"Rule 407. Subsequent Remedial Measures
When measures are taken that would have made an earlier injury
or harm less likely to occur, evidence of the subsequent
measures is not admissible to prove:
• negligence;
• culpable conduct;
• a defect in a product or its design; or
• a need for a warning or instruction.
But the court may admit this evidence for another purpose, such
as impeachment or—if disputed—proving ownership, control, or
the feasibility of precautionary measures.
(As amended Apr. 11, 1997, eff. Dec. 1, 1997; Apr. 26, 2011, eff. Dec.
1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");


INSERT INTO objection VALUES 
(13, 408, "Subsequent Remedial Measures", 
"Rule 408. Compromise Offers and Negotiations
(a) PROHIBITED USES. Evidence of the following is not admissible—on
behalf of any party—either to prove or disprove the validity
or amount of a disputed claim or to impeach by a prior inconsistent
statement or a contradiction:
(1) furnishing, promising, or offering—or accepting, promising
to accept, or offering to accept—a valuable consideration
in compromising or attempting to compromise the claim; and 
Rule 409 FEDERAL RULES OF EVIDENCE 6
(2) conduct or a statement made during compromise negotiations
about the claim—except when offered in a criminal case
and when the negotiations related to a claim by a public office
in the exercise of its regulatory, investigative, or enforcement
authority.
(b) EXCEPTIONS. The court may admit this evidence for another
purpose, such as proving a witness’s bias or prejudice, negating a
contention of undue delay, or proving an effort to obstruct a
criminal investigation or prosecution.
(As amended Apr. 12, 2006, eff. Dec. 1, 2006; Apr. 26, 2011, eff. Dec.
1, 2011.)
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");


INSERT INTO objection VALUES 
(14, 409, "Payment of Medical & Simlar Expenses", 
"Rule 409. Offers to Pay Medical and Similar Expenses
Evidence of furnishing, promising to pay, or offering to pay
medical, hospital, or similar expenses resulting from an injury is
not admissible to prove liability for the injury.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(15, 410, "Inadmissibility of Pleas, Plea Discussions, and Related Statements", 
"Rule 410. Pleas, Plea Discussions, and Related Statements
(a) PROHIBITED USES. In a civil or criminal case, evidence of the
following is not admissible against the defendant who made the
plea or participated in the plea discussions:
(1) a guilty plea that was later withdrawn;
(2) a nolo contendere plea;
(3) a statement made during a proceeding on either of those
pleas under Federal Rule of Criminal Procedure 11 or a comparable
state procedure; or
(4) a statement made during plea discussions with an attorney
for the prosecuting authority if the discussions did not result
in a guilty plea or they resulted in a later-withdrawn
guilty plea.
(b) EXCEPTIONS. The court may admit a statement described in
Rule 410(a)(3) or (4):
(1) in any proceeding in which another statement made during
the same plea or plea discussions has been introduced, if
in fairness the statements ought to be considered together; or
(2) in a criminal proceeding for perjury or false statement,
if the defendant made the statement under oath, on the
record, and with counsel present.
(As amended Pub. L. 94–149, § 1(9), Dec. 12, 1975, 89 Stat. 805; Apr.
30, 1979, eff. Dec. 1, 1980; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(16, 411, "Liability Insurance", 
"Rule 411. Liability Insurance
Evidence that a person was or was not insured against liability
is not admissible to prove whether the person acted negligently or
otherwise wrongfully. But the court may admit this evidence for
another purpose, such as proving a witness’s bias or prejudice or
proving agency, ownership, or control.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 26, 2011, eff. Dec.
1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(17, 602, "Lack of Foundation", 
"Rule 602. Need for Personal Knowledge
A witness may testify to a matter only if evidence is introduced
sufficient to support a finding that the witness has personal
knowledge of the matter. Evidence to prove personal knowledge
may consist of the witness’s own testimony. This rule does not
apply to a witness’s expert testimony under Rule 703.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 25, 1988, eff. Nov.
1, 1988; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
MOCK TRIAL: Rule 1104. Lack of foundation.
 All exhibits and opinions must have the necessary foundations established before they can properly
be admitted into evidence. The objection should be made when the exhibit is offered into evidence, or the
witness is asked to give an opinion. Upon request by opposing counsel, the objecting party must provide
an explanation of where foundation is lacking, if such explanation was not part of the objection. 
 http://c.ymcdn.com/sites/www.iowabar.org/resource/resmgr/mock_tria_/mock_trial_rules_2011.pdf");

INSERT INTO objection VALUES 
(18, 602, "Lack of Personal Knowledge", 
"Rule 602. Need for Personal Knowledge
A witness may testify to a matter only if evidence is introduced
sufficient to support a finding that the witness has personal
knowledge of the matter. Evidence to prove personal knowledge
may consist of the witness’s own testimony. This rule does not
apply to a witness’s expert testimony under Rule 703.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 25, 1988, eff. Nov.
1, 1988; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(19, 602, "Assumes Facts not in Evidence", 
"Rule 602. Need for Personal Knowledge
A witness may testify to a matter only if evidence is introduced
sufficient to support a finding that the witness has personal
knowledge of the matter. Evidence to prove personal knowledge
may consist of the witness’s own testimony. This rule does not
apply to a witness’s expert testimony under Rule 703.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 25, 1988, eff. Nov.
1, 1988; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
MOCK TRIAL: Rule 1103. Assumes facts not in evidence.
Questions must not include as a fact something that has not been shown to exist. ");

INSERT INTO objection VALUES 
(20, 611, "Beyond the Scope", 
"Rule 611. Mode and Order of Examining Witnesses and Presenting
Evidence
(a) CONTROL BY THE COURT; PURPOSES. The court should exercise
reasonable control over the mode and order of examining witnesses
and presenting evidence so as to:
(1) make those procedures effective for determining the
truth;
(2) avoid wasting time; and
(3) protect witnesses from harassment or undue embarrassment.
(b) SCOPE OF CROSS-EXAMINATION. Cross-examination should not
go beyond the subject matter of the direct examination and matters
affecting the witness’s credibility. The court may allow inquiry
into additional matters as if on direct examination.
(c) LEADING QUESTIONS. Leading questions should not be used on
direct examination except as necessary to develop the witness’s
testimony. Ordinarily, the court should allow leading questions:
(1) on cross-examination; and
(2) when a party calls a hostile witness, an adverse party, or
a witness identified with an adverse party.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 26, 2011, eff. Dec.
1, 2011.)
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(21, 610, "Religious Beliefs", 
"Rule 610. Religious Beliefs or Opinions
Evidence of a witness’s religious beliefs or opinions is not admissible
to attack or support the witness’s credibility.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 26, 2011, eff. Dec.
1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
");

INSERT INTO objection VALUES 
(22, 611, "Leading", 
"Rule 611. Mode and Order of Examining Witnesses and Presenting
Evidence
(a) CONTROL BY THE COURT; PURPOSES. The court should exercise
reasonable control over the mode and order of examining witnesses
and presenting evidence so as to:
(1) make those procedures effective for determining the
truth;
(2) avoid wasting time; and
(3) protect witnesses from harassment or undue embarrassment.
(b) SCOPE OF CROSS-EXAMINATION. Cross-examination should not
go beyond the subject matter of the direct examination and matters
affecting the witness’s credibility. The court may allow inquiry
into additional matters as if on direct examination.
(c) LEADING QUESTIONS. Leading questions should not be used on
direct examination except as necessary to develop the witness’s
testimony. Ordinarily, the court should allow leading questions:
(1) on cross-examination; and
(2) when a party calls a hostile witness, an adverse party, or
a witness identified with an adverse party.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 26, 2011, eff. Dec.
1, 2011.)
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.");

INSERT INTO objection VALUES 
(23, 701, "Speculation", 
"Rule 701. Opinion Testimony by Lay Witnesses
If a witness is not testifying as an expert, testimony in the form
of an opinion is limited to one that is:
(a) rationally based on the witness’s perception;
(b) helpful to clearly understanding the witness’s testimony
or to determining a fact in issue; and
(c) not based on scientific, technical, or other specialized
knowledge within the scope of Rule 702.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 17, 2000, eff. Dec.
1, 2000; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
");

INSERT INTO objection VALUES 
(24, 701, "Lack of Professional Knowledge", 
"Rule 701. Opinion Testimony by Lay Witnesses
If a witness is not testifying as an expert, testimony in the form
of an opinion is limited to one that is:
(a) rationally based on the witness’s perception;
(b) helpful to clearly understanding the witness’s testimony
or to determining a fact in issue; and
(c) not based on scientific, technical, or other specialized
knowledge within the scope of Rule 702.
(As amended Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 17, 2000, eff. Dec.
1, 2000; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
");


INSERT INTO objection VALUES 
(25, 704, "Ultimate Issue", 
"Rule 704. Opinion on an Ultimate Issue
(a) IN GENERAL—NOT AUTOMATICALLY OBJECTIONABLE. An opinion
is not objectionable just because it embraces an ultimate
issue.
(b) EXCEPTION. In a criminal case, an expert witness must not
state an opinion about whether the defendant did or did not have
a mental state or condition that constitutes an element of the 
crime charged or of a defense. Those matters are for the trier of
fact alone.
(As amended Pub. L. 98–473, title II, § 406, Oct. 12, 1984, 98 Stat.
2067; Apr. 26, 2011, eff. Dec. 1, 2011.) 
http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
");


INSERT INTO objection VALUES 
(26, 802, "Hearsay", 
"Rule 802. The Rule Against Hearsay
Hearsay is not admissible unless any of the following provides
otherwise:
• a federal statute;
• these rules; or
• other rules prescribed by the Supreme Court.
(As amended Apr. 26, 2011, eff. Dec. 1, 2011.) 
Rule 801. Definitions That Apply to This Article; Exclusions from
Hearsay
(a) STATEMENT. ‘‘Statement’’ means a person’s oral assertion,
written assertion, or nonverbal conduct, if the person intended it
as an assertion. 
(b) DECLARANT. ‘‘Declarant’’ means the person who made the
statement.
(c) HEARSAY. ‘‘Hearsay’’ means a statement that:
(1) the declarant does not make while testifying at the current
trial or hearing; and
(2) a party offers in evidence to prove the truth of the matter
asserted in the statement.
(d) STATEMENTS THAT ARE NOT HEARSAY. A statement that
meets the following conditions is not hearsay:
(1) A Declarant-Witness’s Prior Statement. The declarant testifies
and is subject to cross-examination about a prior statement,
and the statement:
(A) is inconsistent with the declarant’s testimony and
was given under penalty of perjury at a trial, hearing, or
other proceeding or in a deposition;
(B) is consistent with the declarant’s testimony and is
offered:
(i) to rebut an express or implied charge that the declarant
recently fabricated it or acted from a recent
improper influence or motive in so testifying; or
(ii) to rehabilitate the declarant’s credibility as a
witness when attacked on another ground; or
(C) identifies a person as someone the declarant perceived
earlier.
(2) An Opposing Party’s Statement. The statement is offered
against an opposing party and:
(A) was made by the party in an individual or representative
capacity;
(B) is one the party manifested that it adopted or believed
to be true;
(C) was made by a person whom the party authorized to
make a statement on the subject;
(D) was made by the party’s agent or employee on a matter
within the scope of that relationship and while it existed;
or
(E) was made by the party’s coconspirator during and in
furtherance of the conspiracy.
The statement must be considered but does not by itself establish
the declarant’s authority under (C); the existence or
scope of the relationship under (D); or the existence of the
conspiracy or participation in it under (E).
(As amended Pub. L. 94–113, § 1, Oct. 16, 1975, 89 Stat. 576, eff. Oct.
31, 1975; Mar. 2, 1987, eff. Oct. 1, 1987; Apr. 11, 1997, eff. Dec. 1, 1997;
Apr. 26, 2011, eff. Dec. 1, 2011; Apr. 25, 2014, eff. Dec. 1, 2014.) 

http://www.uscourts.gov/sites/default/files/Rules%20of%20Evidence.
");

INSERT INTO objection VALUES
(27, 1106, "Unresponsive", "MOCK TRIAL: Rule 1106. Unresponsive.
 An answer that does not directly respond to a question is objectionable as unresponsive. Where the
answer goes beyond what is necessary to answer the question, the surplus of the answer is objectionable
as unresponsive. Only the party examining the witness can make this objection
(This rule exists, it should almost never be used) 
http://c.ymcdn.com/sites/www.iowabar.org/resource/resmgr/mock_tria_/mock_trial_rules_2011.pdf"); 

INSERT INTO objection VALUES
(28, 1107, "Unfair Extrapolation", "MOCK TRIAL: Rule 1107. Unfair extrapolations.
 If a witness invents facts on direct examination which affect the outcome of the trial, the opposing
attorney may object that the witness has gone beyond the scope of the affidavit. 
http://c.ymcdn.com/sites/www.iowabar.org/resource/resmgr/mock_tria_/mock_trial_rules_2011.pdf"); 

