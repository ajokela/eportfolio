<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="ResidencyPS">
		<xsl:param name="entryName">
			Residency of Record
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">

					<!-- xsl:value-of select="entryName/value"/><br/> <xsl:value-of select="residency/value"/><br/> 
						<xsl:value-of select="residencyDt/value"/><br/> <xsl:value-of select="effectiveTerm/value"/><br/ -->
					<xsl:value-of select="residencyDesc/value" /><!-- br/> 
						<xsl:value-of select="termDesc/value"/><br/ -->


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
