<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="TransSkills">
		<xsl:param name="entryName">
			Transferable Skills
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">

					<xsl:choose>
						<xsl:when
							test="((surveyDate/value/text()) != ' ') or ((selfKnowledgeTotal/value/text()) != ' ') or ((selfKnowledgeAve/value/text()) != ' ') or ((effectiveCommTotal/value/text()) != ' ') or ((effectiveCommAve/value/text()) != ' ') or ((processControlTotal/value/text()) != ' ') or ((processControlAve/value/text()) != ' ') or ((visioningTotal/value/text()) != ' ') or ((visioningAve/value/text()) != ' ') or ((interpretation/value/text()) != ' ')">
							<xsl:if test="surveyDate/value">
								<strong>Date Taken: </strong>
								<xsl:value-of select="surveyDate/value" />
								<br />
							</xsl:if>
							<br />
							<strong>Scores:</strong>
							<br />
							<xsl:if
								test="((selfKnowledgeTotal/value/text()) != ' ') or ((selfKnowledgeAve/value/text()) != ' ')">
								<strong>Self Knowledge Score: </strong>
								<xsl:if test="selfKnowledgeTotal/value">
									<xsl:value-of select="selfKnowledgeTotal/value" />
									total;
								</xsl:if>
								<xsl:if test="selfKnowledgeAve/value">
									<xsl:value-of select="selfKnowledgeAve/value" />
									average
								</xsl:if>
								<br />
							</xsl:if>
							<xsl:if
								test="((effectiveCommTotal/value/text()) != ' ') or ((effectiveCommAve/value/text()) != ' ')">
								<strong>Effective Communication Score: </strong>
								<xsl:if test="effectiveCommTotal/value">
									<xsl:value-of select="effectiveCommTotal/value" />
									total;
								</xsl:if>
								<xsl:if test="effectiveCommAve/value">
									<xsl:value-of select="effectiveCommAve/value" />
									average
								</xsl:if>
								<br />
							</xsl:if>
							<xsl:if
								test="((processControlTotal/value/text()) != ' ') or ((processControlAve/value/text()) != ' ')">
								<strong>Process Control Score: </strong>
								<xsl:if test="processControlTotal/value">
									<xsl:value-of select="processControlTotal/value" />
									total;
								</xsl:if>
								<xsl:if test="processControlAve/value">
									<xsl:value-of select="processControlAve/value" />
									average
								</xsl:if>
								<br />
							</xsl:if>
							<xsl:if
								test="((visioningTotal/value/text()) != ' ') or ((visioningAve/value/text()) != ' ')">
								<strong>Visioning Score: </strong>
								<xsl:if test="visioningTotal/value">
									<xsl:value-of select="visioningTotal/value" />
									total;
								</xsl:if>
								<xsl:if test="visioningAve/value">
									<xsl:value-of select="visioningAve/value" />
									average
								</xsl:if>
								<br />
							</xsl:if>
							<xsl:if test="interpretation/value">
								<strong>Interpretation: </strong>
								<br />
								<xsl:value-of disable-output-escaping="yes"
									select="interpretation/value" />
								<br />
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[None Entered]</font>
						</xsl:otherwise>
					</xsl:choose>

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
