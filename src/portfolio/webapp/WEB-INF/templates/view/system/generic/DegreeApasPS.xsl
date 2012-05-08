<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="DegreeApasPS">
		<xsl:param name="entryName">
			APAS Report
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
				</td>
				<td class="elemPresentationCell2">
					<a target="ospiApas">
						<xsl:attribute name="href">viewApasReport.do?personId=<xsl:value-of
							select="encryptedPersonId/value" />&amp;entryId=<xsl:value-of
							select="entryId/value" /></xsl:attribute>
						<xsl:value-of select="entryName/value" />
					</a>

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
