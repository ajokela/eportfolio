<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">


	<xsl:template match="Teaching">
		<xsl:param name="entryName">
			Teaching
		</xsl:param>


		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<xsl:if test="fromDate/value">
								<fo:inline font-weight="bold">From:</fo:inline>
								<xsl:value-of select="fromDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="toDate/value">
								<fo:inline font-weight="bold">To:</fo:inline>
								<xsl:value-of select="toDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="courses/value">
								<fo:inline font-weight="bold">Course/Subjects:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="courses/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="organization/value">
								<fo:inline font-weight="bold">Institution/Organization:</fo:inline>
								<xsl:value-of select="organization/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="street1/value">
								<fo:inline font-weight="bold">Address:</fo:inline>
								<xsl:value-of select="street1/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="street2/value">
								<fo:inline font-weight="bold">Address 2:</fo:inline>
								<xsl:value-of select="street2/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="city/value">
								<fo:inline font-weight="bold">City:</fo:inline>
								<xsl:value-of select="city/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="state/value">
								<fo:inline font-weight="bold">State:</fo:inline>
								<xsl:value-of select="state/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="zip/value">
								<fo:inline font-weight="bold">Zip:</fo:inline>
								<xsl:value-of select="zip/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="country/value">
								<fo:inline font-weight="bold">Country:</fo:inline>
								<xsl:value-of select="country/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="comments/value">
								<fo:inline font-weight="bold">Additional Comments:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="comments/value" />
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
