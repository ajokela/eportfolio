<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="AcadPlan">
		<xsl:param name="entryName">
			Academic Plan
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<xsl:if test="subject1/value">
						<strong>
							<xsl:value-of select="subject1/value" />
						</strong>
						<xsl:text> </xsl:text>
						<strong>
							<xsl:value-of select="course1/value" />
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="title1/value">
						<xsl:value-of select="title1/value" />
						<br />
					</xsl:if>
					<xsl:if test="class1/value">
						Class Number
						<xsl:value-of select="class1/value" />
						<br />
					</xsl:if>
					<xsl:if test="credits1/value">
						<xsl:value-of select="credits1/value" />
						credits
						<br />
					</xsl:if>

					<xsl:if
						test="(subject2/value) or (course2/value) or (title2/value) or (class2/value) or (credits2/value)">
						<br />
					</xsl:if>
					<xsl:if test="subject2/value">
						<strong>
							<xsl:value-of select="subject2/value" />
						</strong>
						<xsl:text> </xsl:text>
						<strong>
							<xsl:value-of select="course2/value" />
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="title2/value">
						<xsl:value-of select="title2/value" />
						<br />
					</xsl:if>
					<xsl:if test="class2/value">
						Class Number
						<xsl:value-of select="class2/value" />
						<br />
					</xsl:if>
					<xsl:if test="credits2/value">
						<xsl:value-of select="credits2/value" />
						credits
						<br />
					</xsl:if>

					<xsl:if
						test="(subject3/value) or (course3/value) or (title3/value) or (class3/value) or (credits3/value)">
						<br />
					</xsl:if>
					<xsl:if test="subject3/value">
						<strong>
							<xsl:value-of select="subject3/value" />
						</strong>
						<xsl:text> </xsl:text>
						<strong>
							<xsl:value-of select="course3/value" />
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="title3/value">
						<xsl:value-of select="title3/value" />
						<br />
					</xsl:if>
					<xsl:if test="class3/value">
						Class Number
						<xsl:value-of select="class3/value" />
						<br />
					</xsl:if>
					<xsl:if test="credits3/value">
						<xsl:value-of select="credits3/value" />
						credits
						<br />
					</xsl:if>

					<xsl:if
						test="(subject4/value) or (course4/value) or (title4/value) or (class4/value) or (credits4/value)">
						<br />
					</xsl:if>
					<xsl:if test="subject4/value">
						<strong>
							<xsl:value-of select="subject4/value" />
						</strong>
						<xsl:text> </xsl:text>
						<strong>
							<xsl:value-of select="course4/value" />
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="title4/value">
						<xsl:value-of select="title4/value" />
						<br />
					</xsl:if>
					<xsl:if test="class4/value">
						Class Number
						<xsl:value-of select="class4/value" />
						<br />
					</xsl:if>
					<xsl:if test="credits4/value">
						<xsl:value-of select="credits4/value" />
						credits
						<br />
					</xsl:if>

					<xsl:if
						test="(subject5/value) or (course5/value) or (title5/value) or (class5/value) or (credits5/value)">
						<br />
					</xsl:if>
					<xsl:if test="subject5/value">
						<strong>
							<xsl:value-of select="subject5/value" />
						</strong>
						<xsl:text> </xsl:text>
						<strong>
							<xsl:value-of select="course5/value" />
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="title5/value">
						<xsl:value-of select="title5/value" />
						<br />
					</xsl:if>
					<xsl:if test="class5/value">
						Class Number
						<xsl:value-of select="class5/value" />
						<br />
					</xsl:if>
					<xsl:if test="credits5/value">
						<xsl:value-of select="credits5/value" />
						credits
						<br />
					</xsl:if>

					<xsl:if
						test="(subject6/value) or (course6/value) or (title6/value) or (class6/value) or (credits6/value)">
						<br />
					</xsl:if>
					<xsl:if test="subject6/value">
						<strong>
							<xsl:value-of select="subject6/value" />
						</strong>
						<xsl:text> </xsl:text>
						<strong>
							<xsl:value-of select="course6/value" />
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="title6/value">
						<xsl:value-of select="title6/value" />
						<br />
					</xsl:if>
					<xsl:if test="class6/value">
						Class Number
						<xsl:value-of select="class6/value" />
						<br />
					</xsl:if>
					<xsl:if test="credits6/value">
						<xsl:value-of select="credits6/value" />
						credits
						<br />
					</xsl:if>

					<xsl:if
						test="(subject7/value) or (course7/value) or (title7/value) or (class7/value) or (credits7/value)">
						<br />
					</xsl:if>
					<xsl:if test="subject7/value">
						<strong>
							<xsl:value-of select="subject7/value" />
						</strong>
						<xsl:text> </xsl:text>
						<strong>
							<xsl:value-of select="course7/value" />
						</strong>
						<br />
					</xsl:if>
					<xsl:if test="title7/value">
						<xsl:value-of select="title7/value" />
						<br />
					</xsl:if>
					<xsl:if test="class7/value">
						Class Number
						<xsl:value-of select="class7/value" />
						<br />
					</xsl:if>
					<xsl:if test="credits7/value">
						<xsl:value-of select="credits7/value" />
						credits
						<br />
					</xsl:if>
					<xsl:if test="comments/value">
						<br />
						<strong>Comments: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="comments/value" />
						<br />
					</xsl:if>
				</td>
				<td class="elemPresentationCell3">

					<xsl:choose>
						<xsl:when test="elementMaterials/material">
							<xsl:apply-templates select="elementMaterials" />
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[No Materials]</font>
						</xsl:otherwise>
					</xsl:choose>

				</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
