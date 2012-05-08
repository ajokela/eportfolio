<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="Communication">
		<xsl:param name="entryName">
			Communication Skills
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
							<xsl:if test="expository/value">
								<fo:inline font-weight="bold">Expository Writing:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="expository/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="creative/value">
								<fo:inline font-weight="bold">Creative Writing</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="creative/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="discipline/value">
								<fo:inline font-weight="bold">Writing Within Discipline</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="discipline/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="oneOnOne/value">
								<fo:inline font-weight="bold">One on One Interaction:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="oneOnOne/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="smallGroup/value">
								<fo:inline font-weight="bold">Small Group Communication:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="smallGroup/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="facilitation/value">
								<fo:inline font-weight="bold">Small Group Facilitation:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="facilitation/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="publicSpeaking/value">
								<fo:inline font-weight="bold">Public Speaking:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="publicSpeaking/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="listening/value">
								<fo:inline font-weight="bold">Listening:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="listening/value" />
								<fo:block></fo:block>
							</xsl:if>
							<xsl:if test="conflictRes/value">
								<fo:inline font-weight="bold">Conflict Resolution:</fo:inline>
								<fo:block></fo:block>
								<xsl:apply-templates select="conflictRes/value" />
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
