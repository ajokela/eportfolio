<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="KolbLearning">
		<xsl:param name="entryName">
			Kolb Learning Style Inventory
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:text> </xsl:text>
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Name of Inventory:</strong><xsl:text> 
						</xsl:text><xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="dateTaken/value">
						<strong>Date Taken:</strong>
						<xsl:text>  </xsl:text>
						<xsl:value-of select="dateTaken/value" />
						<br />
					</xsl:if>
					<xsl:if
						test="(concreteExperience/value) or (reflectiveObservation/value) or (abstractConceptualization/value) or (activeExperimentation/value)">
						<br />
						<strong>Learning Cycle Stages Scores</strong>
						<br />
					</xsl:if>
					<xsl:if test="concreteExperience/value">
						<strong>Concrete Experience (CE):</strong>
						<xsl:text>  </xsl:text>
						<xsl:value-of select="concreteExperience/value" />
						<br />
					</xsl:if>
					<xsl:if test="reflectiveObservation/value">
						<strong>Reflective Observation (RO):</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="reflectiveObservation/value" />
						<br />
					</xsl:if>
					<xsl:if test="abstractConceptualization/value">
						<strong>Abstract Conceptualization (AC):</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="abstractConceptualization/value" />
						<br />
					</xsl:if>
					<xsl:if test="activeExperimentation/value">
						<strong>Active Experimentation (AE):</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="activeExperimentation/value" />
						<br />
					</xsl:if>
					<xsl:if test="learningStyleType/value">
						<br />
						<strong>Learning Style Type:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="learningStyleType/value" />
						<br />
					</xsl:if>
					<xsl:if test="interpretation/value">
						<strong>Interpretation/Reaction:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of disable-output-escaping="yes"
							select="interpretation/value" />
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
