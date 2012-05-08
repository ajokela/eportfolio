<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="WorkHistory">
		<xsl:param name="entryName">
			Work History
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
							test="((institution/value/text()) != ' ') or ((supervisor/value/text()) != ' ') or ((address1/value/text()) != ' ') or ((address2/value/text()) != ' ') or ((city/value/text()) != ' ') or ((state/value/text()) != ' ') or ((zip/value/text()) != ' ') or ((country/value/text()) != ' ') or ((telephone/value/text()) != ' ') or ((fax/value/text()) != ' ') or ((startDate/value/text()) != ' ') or ((endDate/value/text()) != ' ') or ((description/value/text()) != ' ') or ((accomplishments/value/text()) != ' ')">
							<xsl:if test="institution/value">
								<strong>Institution: </strong>
								<xsl:value-of select="institution/value" />
								<br />
							</xsl:if>
							<xsl:if test="supervisor/value">
								<strong>Supervisor: </strong>
								<xsl:value-of select="supervisor/value" />
								<br />
							</xsl:if>
							<xsl:if test="address1/value">
								<strong>Address: </strong>
								<xsl:value-of select="address1/value" />
								<br />
							</xsl:if>
							<xsl:if test="address2/value">
								<strong>Address 2: </strong>
								<xsl:value-of select="address2/value" />
								<br />
							</xsl:if>
							<xsl:if test="city/value">
								<strong>City: </strong>
								<xsl:value-of select="city/value" />
								<br />
							</xsl:if>
							<xsl:if test="state/value">
								<strong>State: </strong>
								<xsl:value-of select="state/value" />
								<br />
							</xsl:if>
							<xsl:if test="zip/value">
								<strong>Zip: </strong>
								<xsl:value-of select="zip/value" />
								<br />
							</xsl:if>
							<xsl:if test="country/value">
								<strong>Country: </strong>
								<xsl:value-of select="country/value" />
								<br />
							</xsl:if>
							<xsl:if test="telephone/value">
								<strong>Phone: </strong>
								<xsl:value-of select="telephone/value" />
								<br />
							</xsl:if>
							<xsl:if test="fax/value">
								<strong>Fax: </strong>
								<xsl:value-of select="fax/value" />
								<br />
							</xsl:if>
							<xsl:if test="startDate/value">
								<strong>Start Date: </strong>
								<xsl:value-of select="startDate/value" />
								<br />
							</xsl:if>
							<xsl:if test="endDate/value">
								<strong>End Date: </strong>
								<xsl:value-of select="endDate/value" />
								<br />
							</xsl:if>
							<xsl:if test="presentlyEmployed/value">
								(presently employed)
								<br />
							</xsl:if>
							<xsl:if test="description/value">
								<strong>Description: </strong>
								<br />
								<xsl:value-of disable-output-escaping="yes"
									select="description/value" />
								<br />
							</xsl:if>
							<xsl:if test="accomplishments/value">
								<strong>
									Accomplishments:
									<br />
								</strong>
								<xsl:value-of disable-output-escaping="yes"
									select="accomplishments/value" />
								<br />
							</xsl:if>
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
