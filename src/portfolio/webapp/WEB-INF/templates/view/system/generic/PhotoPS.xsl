<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="PhotoPS">
		<xsl:param name="entryName">
			UCard Photo
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
				</td>
				<td class="elemPresentationCell2">
					<img border="0" height="120">
						<xsl:attribute name="src">UCardPicture?personid=<xsl:value-of
							select="encryptedEmplId/value" /></xsl:attribute>
					</img>
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