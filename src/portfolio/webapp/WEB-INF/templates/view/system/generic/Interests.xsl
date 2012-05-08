<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="Interests">
		<xsl:param name="entryName">
			Personal Interest
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
					<!-- xsl:if test="entryName/value"> <strong>Name of Interest:</strong><xsl:text> 
						</xsl:text><xsl:value-of select="entryName/value"/> <br/> </xsl:if -->
					<xsl:if test="interestActivity/value">
						<strong>Relevant Activities:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="interestActivity/value" />
						<br />
					</xsl:if>
					<xsl:if test="interestSkills/value">
						<strong>Relevant Skills:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="interestSkills/value" />
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
