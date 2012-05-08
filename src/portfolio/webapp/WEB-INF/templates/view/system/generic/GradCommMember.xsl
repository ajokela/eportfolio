<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="GradCommMember">
		<xsl:param name="entryName">
			Graduation Committee Member
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:text> </xsl:text>
					<xsl:value-of select="firstName/value" />
					<xsl:text> </xsl:text>
					<xsl:value-of select="entryName/value" />
				</td>
				<td class="elemPresentationCell2">
					<!-- strong>Name of Committee Member:</strong><xsl:text> </xsl:text> 
						<xsl:if test="firstName/value"> <xsl:value-of select="firstName/value"/><xsl:text> 
						</xsl:text> </xsl:if> <xsl:if test="entryName/value"> <xsl:value-of select="entryName/value"/> 
						</xsl:if> <br/ -->
					<strong>Type of Committee:</strong>
					<xsl:text> </xsl:text>
					<br />
					<xsl:if test="masters/value">
						Masters
						<br />
					</xsl:if>
					<xsl:if test="docPrelimWritten/value">
						Doctoral Preliminary Written
						<br />
					</xsl:if>
					<xsl:if test="docPrelimOral/value">
						Doctoral Preliminary Oral
						<br />
					</xsl:if>
					<xsl:if test="docFinalOral/value">
						Doctoral Final Oral
						<br />
					</xsl:if>
					<strong>Role:</strong>
					<xsl:text> </xsl:text>
					<br />
					<xsl:if test="adviser/value">
						Advisor
						<br />
					</xsl:if>
					<xsl:if test="majorFieldChair/value">
						Major Field Chair
						<br />
					</xsl:if>
					<xsl:if test="majorFieldMember/value">
						Major Field Member
						<br />
					</xsl:if>
					<xsl:if test="majorFieldReviewer/value">
						Major Field Reviewer
						<br />
					</xsl:if>
					<xsl:if test="supportingFieldChair/value">
						Supporting Field Chair
						<br />
					</xsl:if>
					<xsl:if test="supportingFieldMember/value">
						Supporting Field Member
						<br />
					</xsl:if>
					<xsl:if test="supportingFieldReviewer/value">
						Supporting Field Reviewer
						<br />
					</xsl:if>


					<xsl:if test="campusAddress/value">
						<strong>Campus Address:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="campusAddress/value" />
						<br />
					</xsl:if>
					<xsl:if test="phone/value">
						<strong>Phone:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="phone/value" />
						<br />
					</xsl:if>
					<xsl:if test="emailAddress/value">
						<strong>Email Address:</strong>
						<xsl:text> </xsl:text>
						<xsl:value-of select="emailAddress/value" />
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
