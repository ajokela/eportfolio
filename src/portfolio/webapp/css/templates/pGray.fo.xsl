<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:html="http://www.w3.org/1999/xhtml">

    <!-- Extra in fo -->
    <xsl:param name="headerImageFile">images/spacer.gif</xsl:param>
    <xsl:param name="siloURL"></xsl:param>
    <xsl:param name="headerImageURL" select="concat('url(',$siloURL,'/', $headerImageFile, ')')"/>

     <!-- Extra in fo -->
    <xsl:attribute-set name="region-body">
         <xsl:attribute name="column-gap">12pt</xsl:attribute>
         <xsl:attribute name="column-count">1</xsl:attribute>
         <xsl:attribute name="margin-left">.5in</xsl:attribute>
         <xsl:attribute name="margin-bottom">.25in</xsl:attribute>
         <xsl:attribute name="margin-right">.5in</xsl:attribute>
         <xsl:attribute name="margin-top">.5in</xsl:attribute>
   </xsl:attribute-set>

    <xsl:attribute-set name="body">
        <xsl:attribute name="font-family">Helvetica, Arial, sans-serif</xsl:attribute>
        <xsl:attribute name="font-size">80%</xsl:attribute>
        <!-- This property is changed from its value in css file -->
        <xsl:attribute name="color">#333</xsl:attribute>
    </xsl:attribute-set>

     <!-- Extra in fo -->
    <xsl:attribute-set name="footer">
        <xsl:attribute name="font-family">Helvetica, Arial, sans-serif</xsl:attribute>
        <xsl:attribute name="font-size">50%</xsl:attribute>
        <!-- This property is changed from its value in css file -->
        <xsl:attribute name="color">#333</xsl:attribute>
        <xsl:attribute name="text-align">right</xsl:attribute>
    </xsl:attribute-set>


    <!--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
         Block-level
    =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-->


    <xsl:attribute-set name="pHeadingL1">
        <xsl:attribute name="margin-left">1.3em</xsl:attribute>
        <xsl:attribute name="margin-right">2em</xsl:attribute>
        <xsl:attribute name="margin-top">1em</xsl:attribute>
        <xsl:attribute name="padding">.25em</xsl:attribute>
        <xsl:attribute name="vertical-align">middle</xsl:attribute>
        <xsl:attribute name="background-color">#333</xsl:attribute>
        <xsl:attribute name="color">#FFFFFF</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="pHeadingL2">
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="margin-left">4em</xsl:attribute>
        <xsl:attribute name="margin-top">0.5em</xsl:attribute> <!-- different from css -->
        <xsl:attribute name="margin-right">2em</xsl:attribute>
        <xsl:attribute name="font-size">.9em</xsl:attribute>
        <xsl:attribute name="border-bottom">1px solid #333</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="table.pTitle">
        <xsl:attribute name="color">#333</xsl:attribute>
        <xsl:attribute name="background-color">#FFF</xsl:attribute>
        <xsl:attribute name="margin-left">1em</xsl:attribute>
        <xsl:attribute name="margin-right">1.5em</xsl:attribute>
        <xsl:attribute name="margin-top">2.5em</xsl:attribute>
        <xsl:attribute name="margin-bottom">1.75em</xsl:attribute>
        <xsl:attribute name="border-collapse">separate</xsl:attribute> <!-- different from css -->
        <xsl:attribute name="width">100%</xsl:attribute>
        <xsl:attribute name="height">120px</xsl:attribute>
        <xsl:attribute name="vertical-align">top</xsl:attribute>
        <xsl:attribute name="table-layout">fixed</xsl:attribute> <!-- Extra in fo -->
        <xsl:attribute name="inline-progression-dimension">100%</xsl:attribute> <!-- Extra in fo -->

    </xsl:attribute-set>

    <!-- This is not in css file, added to emulate h1 tag in fo -->
    <xsl:attribute-set name="pTitle.h1">
        <xsl:attribute name="font-size">18pt</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="pTitle.headerImg">
        <xsl:attribute name="src">
    <xsl:value-of select="$headerImageURL" />
  </xsl:attribute>
        <xsl:attribute name="content-height">50%</xsl:attribute>
        <xsl:attribute name="scaling">uniform</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="td.pMetaData">
        <xsl:attribute name="font-size">.75em</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="td.pDescription">
        <xsl:attribute name="font-size">.75em</xsl:attribute>
        <!--<xsl:attribute name="width">75%</xsl:attribute>-->
        <xsl:attribute name="padding-left">2.25em</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="table.pelementTable">
        <xsl:attribute name="background-color">red</xsl:attribute>
        <xsl:attribute name="margin-left">1em</xsl:attribute>    <!-- different from css -->
        <xsl:attribute name="margin-right">2em</xsl:attribute>
        <xsl:attribute name="width">100%</xsl:attribute>
        <xsl:attribute name="table-layout">fixed</xsl:attribute> <!-- Extra in fo -->
        <xsl:attribute name="inline-progression-dimension">100%</xsl:attribute> <!-- Extra in fo -->
    </xsl:attribute-set>

    <xsl:attribute-set name="td.elemPresentationCell1">
        <xsl:attribute name="font-size">.75em</xsl:attribute>
        <xsl:attribute name="color">#333</xsl:attribute>    <!-- different from css -->
        <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:attribute-set>

    <xsl:attribute-set name="td.elemPresentationCell2">
        <xsl:attribute name="font-size">.75em</xsl:attribute>
        <xsl:attribute name="color">#333</xsl:attribute>    <!-- different from css -->
    </xsl:attribute-set>

    <xsl:attribute-set name="td.elemPresentationCell3">
        <xsl:attribute name="font-size">.75em</xsl:attribute>
        <xsl:attribute name="color">#333</xsl:attribute>    <!-- different from css -->
        <xsl:attribute name="text-decoration">underline</xsl:attribute>    <!-- different from css -->
    </xsl:attribute-set>

    <!-- Extra in fo -->
    <xsl:attribute-set name="td.elemPresentationCell3.fileName">
        <xsl:attribute name="font-size">1em</xsl:attribute>
        <xsl:attribute name="color">#333</xsl:attribute>
        <xsl:attribute name="text-decoration">none</xsl:attribute>
    </xsl:attribute-set>

     <!-- Extra in fo -->
    <xsl:attribute-set name="pElementBlock">
        <xsl:attribute name="margin-left">2em</xsl:attribute>
        <xsl:attribute name="margin-top">0.5em</xsl:attribute>
        <xsl:attribute name="margin-right">2em</xsl:attribute>
    </xsl:attribute-set>

    <!-- Extra in fo -->
    <xsl:attribute-set name="elementTable">
        <xsl:attribute name="table-layout">fixed</xsl:attribute>
        <xsl:attribute name="width">100%</xsl:attribute>
        <xsl:attribute name="inline-progression-dimension">100%</xsl:attribute>
    </xsl:attribute-set>

    <xsl:include href="../../WEB-INF/templates/view/system/generic.fo.xsl" />

</xsl:stylesheet>
