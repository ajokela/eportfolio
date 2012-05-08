<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">


	<xsl:template match="ComputerSkills">
		<xsl:param name="entryName">
			Computer Skills
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
							<xsl:if test="generalOps/value">
								<fo:inline font-weight="bold">General Operations:</fo:inline>
								<xsl:value-of select="generalOps/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="commInt/value">
								<fo:inline font-weight="bold">Communication and Internet:</fo:inline>
								<xsl:value-of select="commInt/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="wordProc/value">
								<fo:inline font-weight="bold">Word Processing:</fo:inline>
								<xsl:value-of select="wordProc/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="spreadsheet/value">
								<fo:inline font-weight="bold">Spreadsheet:</fo:inline>
								<xsl:value-of select="spreadsheet/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="database/value">
								<fo:inline font-weight="bold">Database:</fo:inline>
								<xsl:value-of select="database/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="graphics/value">
								<fo:inline font-weight="bold">Graphics:</fo:inline>
								<xsl:value-of select="graphics/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="applications/value">
								<fo:inline font-weight="bold">Applications:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="applications/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="languages/value">
								<fo:inline font-weight="bold">Languages:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="languages/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="experience/value">
								<fo:inline font-weight="bold">Experience:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="experience/value" />
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
