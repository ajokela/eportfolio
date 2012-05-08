<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="ComputerSkills">
		<xsl:param name="entryName">
			Computer Skills
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<xsl:if test="generalOps/value">
						<strong>General Operations: </strong>
						<xsl:value-of select="generalOps/value" />
						<br />
					</xsl:if>
					<xsl:if test="commInt/value">
						<strong>Communication and Internet: </strong>
						<xsl:value-of select="commInt/value" />
						<br />
					</xsl:if>
					<xsl:if test="wordProc/value">
						<strong>Word Processing: </strong>
						<xsl:value-of select="wordProc/value" />
						<br />
					</xsl:if>
					<xsl:if test="spreadsheet/value">
						<strong>Spreadsheet: </strong>
						<xsl:value-of select="spreadsheet/value" />
						<br />
					</xsl:if>
					<xsl:if test="database/value">
						<strong>Database: </strong>
						<xsl:value-of select="database/value" />
						<br />
					</xsl:if>
					<xsl:if test="graphics/value">
						<strong>Graphics: </strong>
						<xsl:value-of select="graphics/value" />
						<br />
					</xsl:if>
					<xsl:if test="applications/value">
						<strong>Applications: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="applications/value" />
						<br />
					</xsl:if>
					<xsl:if test="languages/value">
						<strong>Languages: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="languages/value" />
						<br />
					</xsl:if>
					<xsl:if test="experience/value">
						<strong>Experience: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="experience/value" />
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
