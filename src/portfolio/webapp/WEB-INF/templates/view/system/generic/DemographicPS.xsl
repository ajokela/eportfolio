<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="DemographicPS">
		<xsl:param name="entryName">
			Demographic Information
		</xsl:param>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<xsl:if test="birthdate/value">
						<strong>Birth Date: </strong>
						<xsl:value-of select="birthdate/value" />
						<br />
					</xsl:if>
					<xsl:if test="sex/value">
						<strong>Sex: </strong>
						<xsl:value-of select="sex/value" />
						<br />
					</xsl:if>
					<xsl:if test="citizenshipStatus/value">
						<strong>Citizenship Status: </strong>
						<xsl:value-of select="citizenshipStatus/value" />
						<br />
					</xsl:if>
					<xsl:if test="ethnicGroup/value">
						<strong>Ethnic Group: </strong>
						<xsl:value-of select="ethnicGroup/value" />
						<br />
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
