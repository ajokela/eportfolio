<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="Quant">
		<xsl:param name="entryName">
			Quantitative Reasoning Skills
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
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of skills: </fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="elemAlgebra/value">
								<fo:inline font-weight="bold">Elementary algebra:</fo:inline>
								<xsl:apply-templates select="elemAlgebra/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="colAlgebra/value">
								<fo:inline font-weight="bold">College algebra:</fo:inline>
								<xsl:apply-templates select="colAlgebra/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="geometry/value">
								<fo:inline font-weight="bold">Geometry:</fo:inline>
								<xsl:apply-templates select="geometry/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="trigonometry/value">
								<fo:inline font-weight="bold">Trigonometry :</fo:inline>
								<xsl:apply-templates select="trigonometry/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="calculus/value">
								<fo:inline font-weight="bold">Calculus:</fo:inline>
								<xsl:apply-templates select="calculus/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="higherMath/value">
								<fo:inline font-weight="bold">Higher mathematics:</fo:inline>
								<xsl:apply-templates select="higherMath/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="statistics/value">
								<fo:inline font-weight="bold">Statistics:</fo:inline>
								<xsl:apply-templates select="statistics/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="accounting/value">
								<fo:inline font-weight="bold">Accounting:</fo:inline>
								<xsl:apply-templates select="accounting/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="logic/value">
								<fo:inline font-weight="bold">Logic:</fo:inline>
								<xsl:apply-templates select="logic/value" />
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
