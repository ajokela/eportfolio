<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="StudentTerm">
		<xsl:param name="entryName">
			Registration Record
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">


				<td class="elemPresentationCell1">
					<xsl:choose>
						<xsl:when test="position() > 1">
							<font class="invisible">
								<xsl:value-of select="$entryName" />
							</font>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$entryName" />
						</xsl:otherwise>
					</xsl:choose>
				</td>



				<td class="elemPresentationCell2">
					<a target="ospiTerm">
						<xsl:attribute name="href">viewStudentTerm.do?personId=<xsl:value-of
							select="encryptedPersonId/value" />&amp;entryId=<xsl:value-of
							select="entryId/value" /></xsl:attribute>
						<xsl:value-of select="entryName/value" />
					</a>
					<br />
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
