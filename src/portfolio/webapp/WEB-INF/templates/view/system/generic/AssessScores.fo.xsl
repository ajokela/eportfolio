<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="AssessScores">
		<xsl:param name="entryName">
			Assessment Scores
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
							<xsl:if test="dateTaken/value">
								<fo:inline font-weight="bold">Date Taken:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="dateTaken/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="(section1/value) or (score1/value) or (percent1/value)">
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="section1/value">
								<fo:inline font-weight="bold">
									<xsl:value-of select="section1/value" />
									<xsl:text> </xsl:text>
									Section
								</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="score1/value">
								<fo:inline font-weight="bold">Score:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="score1/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="percent1/value">
								<fo:inline font-weight="bold">Percentile:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="percent1/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="(section2/value) or (score2/value) or (percent2/value)">
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="section2/value">
								<fo:inline font-weight="bold">
									<xsl:value-of select="section2/value" />
									<xsl:text> </xsl:text>
									Section
								</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="score2/value">
								<fo:inline font-weight="bold">Score:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="score2/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="percent2/value">
								<fo:inline font-weight="bold">Percentile:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="percent2/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="(section3/value) or (score3/value) or (percent3/value)">
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="section3/value">
								<fo:inline font-weight="bold">
									<xsl:value-of select="section3/value" />
									<xsl:text> </xsl:text>
									Section
								</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="score3/value">
								<fo:inline font-weight="bold">Score:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="score3/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="percent3/value">
								<fo:inline font-weight="bold">Percentile:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="percent3/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="(section4/value) or (score4/value) or (percent4/value)">
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="section4/value">
								<fo:inline font-weight="bold">
									<xsl:value-of select="section4/value" />
									<xsl:text> </xsl:text>
									Section
								</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="score4/value">
								<fo:inline font-weight="bold">Score:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="score4/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="percent4/value">
								<fo:inline font-weight="bold">Percentile:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="percent4/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="(section5/value) or (score5/value) or (percent5/value)">
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="section5/value">
								<fo:inline font-weight="bold">
									<xsl:value-of select="section5/value" />
									<xsl:text> </xsl:text>
									Section
								</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="score5/value">
								<fo:inline font-weight="bold">Score:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="score5/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="percent5/value">
								<fo:inline font-weight="bold">Percentile:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="percent5/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="(section6/value) or (score6/value) or (percent6/value)">
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="section6/value">
								<fo:inline font-weight="bold">
									<xsl:value-of select="section6/value" />
									<xsl:text> </xsl:text>
									Section
								</fo:inline>
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="score6/value">
								<fo:inline font-weight="bold">Score:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="score6/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="percent6/value">
								<fo:inline font-weight="bold">Percentile:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="percent6/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="composite/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Composite Value:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="composite/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="total/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Total:</fo:inline>
								<xsl:text> </xsl:text>
								<xsl:value-of select="total/value" />
								<fo:block></fo:block>
							</xsl:if>

							<xsl:if test="interpretation/value">
								<fo:block></fo:block>
								<fo:inline font-weight="bold">Interpretation:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="interpretation/value" />
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
