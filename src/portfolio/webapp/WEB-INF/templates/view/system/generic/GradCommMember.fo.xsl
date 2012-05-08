<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="GradCommMember">
		<xsl:param name="entryName">
			Graduation Committee Member
		</xsl:param>

		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:text></xsl:text>
							<xsl:value-of select="firstName/value" />
							<xsl:text></xsl:text>
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- strong>Name of Committee Member:</fo:inline><xsl:text> </xsl:text> 
								<xsl:if test="firstName/value"> <xsl:value-of select="firstName/value"/><xsl:text> 
								</xsl:text> </xsl:if> <xsl:if test="entryName/value"> <xsl:value-of select="entryName/value"/> 
								</xsl:if> <br/ -->
							<fo:inline font-weight="bold">Type of Committee:</fo:inline>
							<xsl:text></xsl:text>
							<fo:block></fo:block>
							<xsl:if test="masters/value">
								Masters
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="docPrelimWritten/value">
								Doctoral Preliminary Written
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="docPrelimOral/value">
								Doctoral Preliminary Oral
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="docFinalOral/value">
								Doctoral Final Oral
								<fo:block></fo:block>
							</xsl:if>
							<fo:inline font-weight="bold">Role:</fo:inline>
							<xsl:text></xsl:text>
							<fo:block></fo:block>
							<xsl:if test="adviser/value">
								Advisor
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="majorFieldChair/value">
								Major Field Chair
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="majorFieldMember/value">
								Major Field Member
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="majorFieldReviewer/value">
								Major Field Reviewer
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="supportingFieldChair/value">
								Supporting Field Chair
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="supportingFieldMember/value">
								Supporting Field Member
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="supportingFieldReviewer/value">
								Supporting Field Reviewer
								<fo:block></fo:block>
							</xsl:if>


							<xsl:if test="campusAddress/value">
								<fo:inline font-weight="bold">Campus Address:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="campusAddress/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="phone/value">
								<fo:inline font-weight="bold">Phone:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="phone/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="emailAddress/value">
								<fo:inline font-weight="bold">Email Address:</fo:inline>
								<xsl:text></xsl:text>
								<xsl:value-of select="emailAddress/value" />
								<fo:block></fo:block>
							</xsl:if>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell3">
						<fo:block>
							<xsl:choose>
								<xsl:when test="elementMaterials/material">
									<xsl:apply-templates select="elementMaterials" />
								</xsl:when>
							</xsl:choose>
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>

	</xsl:template>
</xsl:stylesheet>
