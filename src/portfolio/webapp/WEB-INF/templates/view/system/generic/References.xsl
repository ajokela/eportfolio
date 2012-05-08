<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="References">
		<xsl:param name="entryName">
			Reference
		</xsl:param>


		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<!-- <xsl:value-of select="elementMetaData/label/value"/> -->
					<xsl:value-of select="$entryName" />
					:
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- xsl:if test="entryName/value"> <strong>Name: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="position/value">
						<strong>Position: </strong>
						<xsl:value-of select="position/value" />
						<br />
					</xsl:if>
					<xsl:if test="organization/value">
						<strong>Institution / Organization: </strong>
						<xsl:value-of select="organization/value" />
						<br />
					</xsl:if>
					<xsl:if test="address1/value">
						<strong>Address: </strong>
						<br />
						<xsl:value-of select="address1/value" />
						<br />
					</xsl:if>
					<xsl:if test="address2/value">
						<xsl:value-of select="address2/value" />
						<br />
					</xsl:if>
					<xsl:if test="city/value">
						<xsl:value-of select="city/value" />
						,
					</xsl:if>
					<xsl:if test="state/value">
						<xsl:value-of select="state/value" />
					</xsl:if>
					<xsl:if test="zip/value">
						<xsl:value-of select="zip/value" />
						<br />
					</xsl:if>
					<xsl:if test="country/value">
						<xsl:value-of select="country/value" />
						<br />
					</xsl:if>
					<xsl:if test="phone/value">
						<strong>Phone: </strong>
						<xsl:value-of select="phone/value" />
						<br />
					</xsl:if>
					<xsl:if test="email/value">
						<strong>E-mail Address: </strong>
						<xsl:value-of select="email/value" />
						<br />
					</xsl:if>
					<strong>Contact Preferences:</strong>
					<br />
					<xsl:if test="mailPref/value=1">
						Mail
						<br />
					</xsl:if>
					<xsl:if test="phonePref/value=1">
						Phone
						<br />
					</xsl:if>
					<xsl:if test="emailPref/value=1">
						E-mail
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
