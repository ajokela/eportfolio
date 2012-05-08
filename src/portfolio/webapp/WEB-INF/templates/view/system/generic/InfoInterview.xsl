<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="InfoInterview">
		<xsl:param name="entryName">
			Informational Interview
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
					<!-- xsl:if test="entryName/value"> <strong>Name of Person Interviewed:</strong><xsl:text> 
						</xsl:text><xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="intDate/value">
						<strong>Date of Interview:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="intDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="occupation/value">
						<strong>Occupation:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="occupation/value" />
						<br />
					</xsl:if>
					<xsl:if test="organization/value">
						<strong>Institution/Organization:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="organization/value" />
						<br />
					</xsl:if>
					<xsl:if test="infoOccupation/value">
						<strong>Information Obtained:</strong>
						<br />
						<xsl:value-of select="infoOccupation/value" />
						<br />
					</xsl:if>
					<xsl:if test="addlNotes/value">
						<strong>Additional Information:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="addlNotes/value" />
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
