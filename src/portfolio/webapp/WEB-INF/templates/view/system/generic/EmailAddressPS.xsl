<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="EmailAddressPS">
		<xsl:param name="entryName">
			Email Address of Record
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
						<xsl:when test="(emailId/value/text()) != ' '">
							<a>
								<xsl:attribute name="href">mailto:<xsl:value-of
									select="emailId/value" /></xsl:attribute>
								<xsl:value-of select="emailId/value" />
							</a>
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[None Entered]</font>
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
