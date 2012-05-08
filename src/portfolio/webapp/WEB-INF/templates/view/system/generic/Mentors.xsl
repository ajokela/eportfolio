<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="Mentors">
		<xsl:param name="entryName">
			Mentor
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:

					<xsl:value-of select="firstName/value" />
					<xsl:text> </xsl:text>
					<xsl:value-of select="entryName/value" />

				</td>
				<td class="elemPresentationCell2">
					<xsl:if test="title/value">
						<strong>Title: </strong>
						<xsl:value-of select="title/value" />
						<br />
					</xsl:if>
					<xsl:if test="organization/value">
						<strong>Organization: </strong>
						<xsl:value-of select="organization/value" />
						<br />
					</xsl:if>
					<xsl:if test="street1/value">
						<strong>Address: </strong>
						<br />
						<xsl:value-of select="street1/value" />
						<br />
					</xsl:if>
					<xsl:if test="street2/value">
						<xsl:value-of select="street2/value" />
						<br />
					</xsl:if>
					<xsl:if test="city/value">
						<xsl:value-of select="city/value" />
						,
						<xsl:text> </xsl:text>
					</xsl:if>
					<xsl:if test="state/value">
						<xsl:value-of select="state/value" />
						<xsl:text> </xsl:text>
					</xsl:if>
					<xsl:if test="zip/value">
						<xsl:value-of select="zip/value" />
						<br />
					</xsl:if>
					<xsl:if test="country/value">
						<xsl:value-of select="country/value" />
						<br />
					</xsl:if>
					<xsl:if test="description/value">
						<strong>Description: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="description/value" />
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
