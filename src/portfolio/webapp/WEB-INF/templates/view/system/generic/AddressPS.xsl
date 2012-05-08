<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="AddressPS">
		<xsl:param name="entryName">
			Address of Record
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
						<xsl:when
							test="((address1/value/text()) != ' ')or((address2/value/text()) != ' ')or((address3/value/text()) != ' ')or((address4/value/text()) != ' ')or((city/value/text()) != ' ')or((state/value/text()) != ' ')or((postal/value/text()) != ' ')or((country/value/text()) != ' ')">
							<xsl:if test="(address1/value/text()) != ' '">
								<xsl:value-of select="address1/value" />
								<br />
							</xsl:if>
							<xsl:if test="(address2/value/text()) != ' '">
								<xsl:value-of select="address2/value" />
								<br />
							</xsl:if>
							<xsl:if test="(address3/value/text()) != ' '">
								<xsl:value-of select="address3/value" />
								<br />
							</xsl:if>
							<xsl:if test="(address4/value/text()) != ' '">
								<xsl:value-of select="address4/value" />
								<br />
							</xsl:if>
							<xsl:if test="(city/value/text()) != ' '">
								<xsl:value-of select="city/value" />
							</xsl:if>
							<xsl:if test="(state/value/text()) != ' '">
								,
								<xsl:value-of select="state/value" />
								<br />
							</xsl:if>
							<xsl:if test="(postal/value/text()) != ' '">
								<xsl:value-of select="postal/value" />
								<br />
							</xsl:if>
							<xsl:if test="(country/value/text()) != ' '">
								<xsl:value-of select="country/value" />
								<br />
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[No Materials]</font>
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
