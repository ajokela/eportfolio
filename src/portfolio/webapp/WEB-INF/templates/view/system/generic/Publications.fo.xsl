<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="Publications">
		<xsl:param name="entryName">
			Publication
		</xsl:param>


		<fo:table xsl:use-attribute-sets="elementTable">
			<fo:table-body>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
						<fo:block>
							<!-- <xsl:value-of select="elementMetaData/label/value"/> -->
							<xsl:value-of select="$entryName" />
							:
							<xsl:value-of select="entryName/value" />
						</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
						<fo:block>
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Title 
								of publication: </fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="author/value">
								<fo:inline font-weight="bold">Author(s):</fo:inline>
								<xsl:apply-templates select="author/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="description/value">
								<fo:inline font-weight="bold">Description / Abstract:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="description/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="publDate/value">
								<fo:inline font-weight="bold">Year of publication:</fo:inline>
								<xsl:value-of select="publDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="editor/value">
								<fo:inline font-weight="bold">Editors(s):</fo:inline>
								<xsl:apply-templates select="editor/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="collTitle/value">
								<fo:inline font-weight="bold">Title of collection:</fo:inline>
								<xsl:value-of select="collTitle/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="collVol/value">
								<fo:inline font-weight="bold">Volume and number of
									collection:</fo:inline>
								<xsl:value-of select="collVol/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="collPage/value">
								<fo:inline font-weight="bold">Page numbers:</fo:inline>
								<xsl:value-of select="collPage/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="pubLoc/value">
								<fo:inline font-weight="bold">Location of publisher:</fo:inline>
								<xsl:value-of select="pubLoc/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="pubName/value">
								<fo:inline font-weight="bold">Name of publisher:</fo:inline>
								<xsl:value-of select="pubName/value" />
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
