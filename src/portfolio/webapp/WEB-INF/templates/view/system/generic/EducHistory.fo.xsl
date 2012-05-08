<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">


	<xsl:template match="EducHistory">
		<xsl:param name="entryName">
			Educational History
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

							<xsl:choose>
								<xsl:when
									test="((degree/value/text()) != ' ') or ((fromDate/value/text()) != ' ') or ((toDate/value/text()) != ' ') or ((degreeDate/value/text()) != ' ')">
									<xsl:if test="degree/value">
										Degree:
										<xsl:value-of select="degree/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="fromDate/value">
										<fo:inline font-weight="bold">From Date:</fo:inline>
										<xsl:value-of select="fromDate/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="toDate/value">
										To Date:
										<xsl:value-of select="toDate/value" />
										<fo:block></fo:block>
									</xsl:if>
									<xsl:if test="degreeDate/value">
										<fo:inline font-weight="bold">Degree Date:</fo:inline>
										<xsl:value-of select="degreeDate/value" />
										<fo:block></fo:block>
									</xsl:if>
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
