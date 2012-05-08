<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="Language">
		<xsl:param name="entryName">
			Language Proficiency
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
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Language: 
								</fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="levelorCode/value">
								<fo:inline font-weight="bold">Oral fluency level:</fo:inline>
								<xsl:if test="levelorCode/value='NO'">
									Novice
								</xsl:if>
								<xsl:if test="levelorCode/value='IN'">
									Intermediate
								</xsl:if>
								<xsl:if test="levelorCode/value='AD'">
									Advanced
								</xsl:if>
								<xsl:if test="levelorCode/value='SU'">
									Superior
								</xsl:if>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="levelwrCode/value">
								<fo:inline font-weight="bold">Written fluency level:</fo:inline>
								<xsl:if test="levelwrCode/value='NO'">
									Novice
								</xsl:if>
								<xsl:if test="levelwrCode/value='IN'">
									Intermediate
								</xsl:if>
								<xsl:if test="levelwrCode/value='AD'">
									Advanced
								</xsl:if>
								<xsl:if test="levelwrCode/value='SU'">
									Superior
								</xsl:if>
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
