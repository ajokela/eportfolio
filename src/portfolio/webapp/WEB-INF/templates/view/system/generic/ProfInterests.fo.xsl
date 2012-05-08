<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="ProfInterests">
		<xsl:param name="entryName">
			Professional Interest
		</xsl:param>

		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<fo:inline font-weight="bold">
								<xsl:value-of select="$entryName" />
								:
							</fo:inline>
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of Interest: </fo:inline><xsl:value-of select="entryName/value"/> <fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="interestActivity/value">
								<fo:inline font-weight="bold">
									Relevant Activities:
									<fo:block></fo:block>
								</fo:inline>
								<xsl:apply-templates select="interestActivity/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="interestSkills/value">
								<fo:inline font-weight="bold">
									Relevant Skills:
									<fo:block></fo:block>
								</fo:inline>
								<xsl:apply-templates select="interestSkills/value" />
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