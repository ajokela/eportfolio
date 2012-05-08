<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="ProDocumentation">
		<xsl:param name="entryName">
			Professional Practices Documentation
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
					<!-- strong>Entry Name: </strong><xsl:value-of select="entryName/value"/><br/ -->
					<strong>Text: </strong>
					<br />
					<!-- xsl:value-of select="text/value"/ -->
					<xsl:variable name="myString" select="text/value" />
					<xsl:value-of disable-output-escaping="yes" select="$myString" />
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
