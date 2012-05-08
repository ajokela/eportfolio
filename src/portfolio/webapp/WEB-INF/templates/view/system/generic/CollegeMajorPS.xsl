<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="CollegeMajorPS">
		<xsl:param name="entryName">
			College and Major
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
							test="((termDesc/value/text()) != ' ')or((effdt/value/text()) != ' ')or((institutionDesc/value/text()) != ' ')or((acadCareerDesc/value/text()) != ' ')or((progActionDescr/value/text()) != ' ')or((progDescr/value/text()) != ' ')or ((planDesc/value/text()) != ' ')">
							<xsl:if test="(termDesc/value/text()) != ' '">
								<strong>Term: </strong>
								<xsl:value-of select="termDesc/value" />
								<br />
							</xsl:if>
							<xsl:if test="(effdt/value/text()) != ' '">
								<xsl:value-of select="effdt/value" />
								(Date of change)
								<br />
							</xsl:if>
							<xsl:if test="(institutionDesc/value/text()) != ' '">
								<strong>Campus: </strong>
								<xsl:value-of select="institutionDesc/value" />
								<br />
							</xsl:if>
							<xsl:if test="(acadCareerDesc/value/text()) != ' '">
								<strong>Career: </strong>
								<xsl:value-of select="acadCareerDesc/value" />
								<br />
							</xsl:if>
							<xsl:if test="(progActionDescr/value/text()) != ' '">
								<strong>Program Action: </strong>
								<xsl:value-of select="progActionDescr/value" />
								<br />
							</xsl:if>
							<xsl:if test="(progDescr/value/text()) != ' '">
								<strong>College: </strong>
								<xsl:value-of select="progDescr/value" />
								<br />
							</xsl:if>
							<xsl:if test="(planDesc/value/text()) != ' '">
								<strong>Major/Minor: </strong>
								<xsl:value-of select="planDesc/value" />
								(
								<xsl:value-of select="planTypeDesc/value" />
								)
								<br />
								<br />
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[No Data Available]</font>
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
