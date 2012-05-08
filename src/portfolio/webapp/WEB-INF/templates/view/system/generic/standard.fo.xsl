<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="elementStandards">
		<xsl:if test="count(standard) > 0">
			<fo:table table-layout="fixed" width="100%" border-collapse="separate">
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell1">
							<fo:block></fo:block>
						</fo:table-cell>

						<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell2">
							<!-- Show standards below descriptive text -->
							<fo:block>
								<fo:inline font-weight="bold">Public Tags:</fo:inline>
								<fo:block></fo:block>
								<xsl:for-each select="standard">
									<xsl:if test="position() > 1">
										<fo:block></fo:block>
									</xsl:if>
									<fo:block>
										-
										<xsl:value-of select="standardText/value" />
									</fo:block>
								</xsl:for-each>
							</fo:block>
						</fo:table-cell>

						<fo:table-cell xsl:use-attribute-sets="td.elemPresentationCell3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>