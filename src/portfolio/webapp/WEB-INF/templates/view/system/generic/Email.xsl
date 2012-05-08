<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="Email">
		<xsl:param name="entryName">
			Email Address
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
						<xsl:when test="(email/value/text()) != ' '">
							<a>
								<xsl:attribute name="href">mailto:<xsl:value-of
									select="email/value" /></xsl:attribute>
								<xsl:value-of select="email/value" />
							</a>
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[None Entered]</font>
						</xsl:otherwise>
					</xsl:choose>
				</td>
				<td class="elemPresentationCell3">
					<xsl:choose>
						<xsl:when test="count(elementMaterials/material) > 0">
							<xsl:for-each select="elementMaterials/material">
								<xsl:if test="position() > 1">
									<br />
								</xsl:if>
								<img src="images/file.gif" width="15" height="17" />
								<a target="ospiMaterial">
									<xsl:attribute name="href"><xsl:value-of
										select="showMaterialUrl/value" /></xsl:attribute>
									<xsl:value-of select="sampleName/value" />
								</a>
								<xsl:text>  </xsl:text>
							</xsl:for-each>
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
