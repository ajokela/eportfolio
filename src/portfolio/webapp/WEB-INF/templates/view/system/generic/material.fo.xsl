<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:template match="elementMaterials">

		<xsl:choose>
			<xsl:when test="count(material) > 0">
				<fo:inline font-weight="bold">Attachments:</fo:inline>
				<fo:block>
				</fo:block>
				<xsl:for-each select="material">
					<xsl:if test="position() > 1">
						<fo:block>
						</fo:block>
					</xsl:if>
					<fo:block>
						<fo:basic-link>
							<xsl:attribute name="external-destination">url(attachments/<xsl:value-of
								select="name/value" />)</xsl:attribute>
							-
							<xsl:value-of select="sampleName/value" />
						</fo:basic-link>
						<fo:inline xsl:use-attribute-sets="td.elemPresentationCell3.fileName">
							(
							<xsl:value-of select="name/value" />
							)
						</fo:inline>

					</fo:block>

				</xsl:for-each>

			</xsl:when>
		</xsl:choose>

	</xsl:template>

</xsl:stylesheet>
