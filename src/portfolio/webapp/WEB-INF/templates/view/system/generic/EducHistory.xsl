<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="EducHistory">
		<xsl:param name="entryName">
			Educational History
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
							test="((degree/value/text()) != ' ') or ((fromDate/value/text()) != ' ') or ((toDate/value/text()) != ' ') or ((degreeDate/value/text()) != ' ')">
							<xsl:if test="degree/value">
								<strong>Degree: </strong>
								<xsl:value-of select="degree/value" />
								<br />
							</xsl:if>
							<xsl:if test="fromDate/value">
								<strong>From Date: </strong>
								<xsl:value-of select="fromDate/value" />
								<br />
							</xsl:if>
							<xsl:if test="toDate/value">
								<strong>To Date: </strong>
								<xsl:value-of select="toDate/value" />
								<br />
							</xsl:if>
							<xsl:if test="degreeDate/value">
								<strong>Degree Date: </strong>
								<xsl:value-of select="degreeDate/value" />
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
