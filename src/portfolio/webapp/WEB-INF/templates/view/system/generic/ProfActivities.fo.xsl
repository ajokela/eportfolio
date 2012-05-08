<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="ProfActivities">
		<xsl:param name="entryName">
			Professional Activities
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
							<!-- xsl:if test="entryName/value"> <fo:inline font-weight="bold">Type 
								of Activity: </fo:inline><xsl:value-of select="entryName/value"/><fo:block></fo:block> 
								</xsl:if -->
							<xsl:if test="name/value">
								<fo:inline font-weight="bold">Name of Practice:</fo:inline>
								<xsl:value-of select="name/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="fromDate/value">
								<fo:inline font-weight="bold">From Date:</fo:inline>
								<xsl:value-of select="fromDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="toDate/value">
								<fo:inline font-weight="bold">To Date:</fo:inline>
								<xsl:value-of select="toDate/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="location/value">
								<fo:inline font-weight="bold">Location:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="location/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="description/value">
								<fo:inline font-weight="bold">Description:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="description/value" />
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
