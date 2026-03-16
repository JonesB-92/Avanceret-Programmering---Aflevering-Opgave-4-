1. Brugte en count til at stoppe infinite while-loops, men i princippet er den ligegyldig, 
    hvis man rehasher ved LoadFactor = 0.5 ? 
   2. Halvvejs igennem fandt jeg ud af, at man selvfølgelig bruger et for-loop, hvilket i min mening er meeeget mere
      læsbart og kræver færre variable at holde styr på.
3. Havde private int n = 13 til at starte med (kopieret direkte fra de andre opgaver), men da n er nødt til
    at være mere fleksibel ved rehashing, fjerner jeg den og bruger bare table.length i stedet.

