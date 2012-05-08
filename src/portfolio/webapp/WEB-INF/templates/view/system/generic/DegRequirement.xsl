<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="DegRequirement">
		<xsl:param name="entryName">
			Degree Requirements
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
					<xsl:if test="bulletinYear/value">
						<strong>Bulletin Year:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="bulletinYear/value" />
						<br />
					</xsl:if>
					<xsl:if test="requirements/value">
						<strong>Requirements:</strong>
						<br />
						<xsl:variable name="myString" select="requirements/value" />
						<xsl:value-of disable-output-escaping="yes" select="$myString" />
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
