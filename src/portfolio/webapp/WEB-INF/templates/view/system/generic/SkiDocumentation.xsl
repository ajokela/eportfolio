<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="SkiDocumentation">
		<xsl:param name="entryName">
			Skills Documentation
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<strong>Text: </strong>
					<br />
					<xsl:variable name="myString" select="text/value" />
					<xsl:value-of disable-output-escaping="yes" select="$myString" />

					<xsl:variable name="myTag" select="tag/value" />
					<xsl:choose>
						<xsl:when test="string-length($myTag) &gt; 0">
							<br />
							<br />
							<br />
							<strong>Public Tags:</strong>
							<br />
							<xsl:value-of select="$myTag" />
							<br />
						</xsl:when>
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
