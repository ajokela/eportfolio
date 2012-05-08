<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="InformationSkills">
		<xsl:param name="entryName">
			Information Skills
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
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Name 
								of Skills:</fo:inline><xsl:text> </xsl:text> <xsl:value-of select="entryName/value"/> 
								<fo:block></fo:block> </xsl:if -->
							<xsl:if test="recognizeNeed/value">
								<fo:inline font-weight="bold">Recognize and Articulate the
									Need for
									Information:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="recognizeNeed/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="accessInfo/value">
								<fo:inline font-weight="bold">Evaluate Information:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="accessInfo/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="evaluateInfo/value">
								<fo:inline font-weight="bold">Access Needed Information:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="evaluateInfo/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="useEffectively/value">
								<fo:inline font-weight="bold">Use Information Effectively to
									Accomplish a Specific
									Purpose:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="useEffectively/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="useEthically/value">
								<fo:inline font-weight="bold">Use Information Ethically and
									Legally:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="useEthically/value" />
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
