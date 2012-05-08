<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="Teaching">
		<xsl:param name="entryName">
			Teaching
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
					<!-- xsl:if test="entryName/value"> <strong>Teaching Position: </strong><xsl:value-of 
						select="entryName/value"/><br/> </xsl:if -->
					<xsl:if test="fromDate/value">
						<strong>From: </strong>
						<xsl:value-of select="fromDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="toDate/value">
						<strong>To: </strong>
						<xsl:value-of select="toDate/value" />
						<br />
					</xsl:if>
					<xsl:if test="courses/value">
						<strong>Course/Subjects:</strong>
						<br />
						<xsl:value-of disable-output-escaping="yes" select="courses/value" />
						<br />
					</xsl:if>
					<xsl:if test="organization/value">
						<strong>Institution/Organization: </strong>
						<xsl:value-of select="organization/value" />
						<br />
					</xsl:if>
					<xsl:if test="street1/value">
						<strong>Address: </strong>
						<xsl:value-of select="street1/value" />
						<br />
					</xsl:if>
					<xsl:if test="street2/value">
						<strong>Address 2: </strong>
						<xsl:value-of select="street2/value" />
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
					<xsl:if test="comments/value">
						<strong>Additional Comments: </strong>
						<br />
						<xsl:value-of disable-output-escaping="yes"
							select="comments/value" />
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
