<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="GradThesis">
		<xsl:param name="entryName">
			Thesis
		</xsl:param>

		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<xsl:value-of select="$entryName" />
							:
							<xsl:text></xsl:text>
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell number-columns-spanned="2">
						<fo:block xsl:use-attribute-sets="td.elemPresentationCell2">
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Thesis 
								Title:</fo:inline><xsl:text> </xsl:text> <xsl:value-of select="entryName/value"/> 
								<fo:block></fo:block> </xsl:if -->
							<xsl:if test="university/value">
								<fo:inline font-weight="bold">Univeristy of Completion:</fo:inline>

								<xsl:text></xsl:text>
								<xsl:value-of select="university/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="defenseDate/value">
								<fo:inline font-weight="bold">Date of Defense:</fo:inline>

								<xsl:text></xsl:text>
								<xsl:value-of select="defenseDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="introduction/value">
								<fo:inline font-weight="bold">Introduction:</fo:inline>

								<fo:block></fo:block>
								<xsl:apply-templates select="introduction/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="commentary/value">
								<fo:inline font-weight="bold">Abstract/Comments:</fo:inline>
								<fo:block></fo:block>
								<fo:block white-space-collapse="false"
									linefeed-treatment="preserve">
									<xsl:apply-templates select="commentary/value" />
								</fo:block>
							</xsl:if>
						</fo:block>
						<fo:block></fo:block>
						<fo:block xsl:use-attribute-sets="td.elemPresentationCell3">
							<xsl:choose>
								<xsl:when test="elementMaterials/material">
									<xsl:apply-templates select="elementMaterials" />
								</xsl:when>

							</xsl:choose>
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
